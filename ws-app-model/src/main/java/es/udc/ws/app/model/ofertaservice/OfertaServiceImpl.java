package es.udc.ws.app.model.ofertaservice;

import static es.udc.ws.app.model.util.ConstantesDelModelo.OFERTA_DATA_SOURCE;
import static es.udc.ws.app.model.util.ConstantesDelModelo.PRECIO_MAX;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import es.udc.es.app.validador.ValidadorPropiedades;
import es.udc.ws.app.exceptions.FechaInvalidaException;
import es.udc.ws.app.exceptions.FechaReservaExpiradaException;
import es.udc.ws.app.exceptions.HayReservasException;
import es.udc.ws.app.exceptions.OfertaYaInvalidadaException;
import es.udc.ws.app.exceptions.OfertaYaReservadaException;
import es.udc.ws.app.exceptions.PrecioInvalidoException;
import es.udc.ws.app.exceptions.ReclamacionAnteriorReservaException;
import es.udc.ws.app.exceptions.ReservaYaReclamadaException;
import es.udc.ws.app.model.oferta.Oferta;
import es.udc.ws.app.model.oferta.SqlOfertaDao;
import es.udc.ws.app.model.oferta.SqlOfertaDaoFactory;
import es.udc.ws.app.model.reserva.Reserva;
import es.udc.ws.app.model.reserva.SqlReservaDao;
import es.udc.ws.app.model.reserva.SqlReservaDaoFactory;
import es.udc.ws.app.reserva.EstadoReserva;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.validation.PropertyValidator;


public class OfertaServiceImpl implements OfertaService{

	
	private DataSource datos;
	private SqlOfertaDao ofertaDao= null;
	private SqlReservaDao reservaDao= null;
	
	
	public OfertaServiceImpl() {
		datos=DataSourceLocator.getDataSource(OFERTA_DATA_SOURCE);
		ofertaDao=SqlOfertaDaoFactory.getDao();
		reservaDao=SqlReservaDaoFactory.getDao();
	}
	
	private void validarOferta(Oferta o) throws InputValidationException {
		
		PropertyValidator.validateMandatoryString("nombre", o.getNombre());PropertyValidator.validateMandatoryString("descripcion", o.getDescripcion());
		ValidadorPropiedades.validateFutureDate("fechaLimReclamacion", o.getFechaLimReclamacion());
		ValidadorPropiedades.validateFutureDate("fechaLimReserva", o.getFechaLimReserva());
		ValidadorPropiedades.validateFloat("precioReal", o.getPrecioReal(), 0, PRECIO_MAX);
		ValidadorPropiedades.validateFloat("precioDescontado", o.getPrecioDescontado(), 0, PRECIO_MAX);
		ValidadorPropiedades.validateFloat("comisionVenta",o.getComisionVenta(),0,PRECIO_MAX);
		
	}
	
	
	@Override
	public Oferta crearOferta(Oferta o) throws InputValidationException, ReclamacionAnteriorReservaException{
		
		validarOferta(o);
		
		if (o.getFechaLimReclamacion().before(o.getFechaLimReserva() ) ) {
			/* Contempplamos el caso de que la FechaLimReclamacion sea anterior a la de Reserva*/
			
			throw new ReclamacionAnteriorReservaException(o.getFechaLimReclamacion(), o.getFechaLimReserva());
		}
		
		try (Connection c = datos.getConnection()) {
			
			try {
				c.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				c.setAutoCommit(false);
				
				Oferta oCreada = ofertaDao.crear(c, o);
				
				c.commit();
				
				return oCreada;
			}
			catch (SQLException e) {
				c.rollback();
                throw new RuntimeException(e);
			}
			catch (RuntimeException | Error e) {
				c.rollback();
				throw e;
			}
			
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	@Override
	public void actualizarOferta(Oferta o) throws InputValidationException,
			InstanceNotFoundException, FechaInvalidaException,PrecioInvalidoException,
			ReclamacionAnteriorReservaException{
		
		validarOferta(o);
		
		try (Connection c = datos.getConnection() ){
			
			c.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			c.setAutoCommit(false);
			
			try {	
				
				if (o.getFechaLimReclamacion().before(o.getFechaLimReserva()) ) {
					throw new ReclamacionAnteriorReservaException(o.getFechaLimReclamacion(), o.getFechaLimReserva());
				}
				
				List<Reserva> reservas = reservaDao.buscar(c, o.getIdOferta());
				
				if (!reservas.isEmpty()) {
					
					Oferta oferta_bd = ofertaDao.buscar(c, o.getIdOferta());
					
					if (o.getFechaLimReclamacion().before(oferta_bd.getFechaLimReclamacion())) {
						c.commit();
						throw new FechaInvalidaException(o.getFechaLimReclamacion());
						
					}
					else if(o.getPrecioReal()!=oferta_bd.getPrecioReal()){
						
						if(o.getPrecioDescontado()>o.getPrecioReal()){
							c.commit();
							throw new PrecioInvalidoException(o.getPrecioDescontado());
						}
						
					}
					else if (o.getPrecioDescontado() > oferta_bd.getPrecioDescontado() ) {
						c.commit();
						throw new PrecioInvalidoException(o.getPrecioDescontado());
					}
					
				}
				if ((o.isInvalida() ) && (!reservas.isEmpty()) ) {
					/*En el caso de que se invalide la oferta, anulamos las reservas asociadas */
					reservaDao.actualizar(c, EstadoReserva.ANULADA, o.getIdOferta());
					
				}
				
				ofertaDao.actualizar(c, o);
				c.commit();
				
			}
			catch (ReclamacionAnteriorReservaException e ) {
				c.commit();
				throw e;
			}
			catch (InstanceNotFoundException e) {
				c.commit();
				throw e;
			}
			catch (SQLException e) {
				c.rollback();
				throw new RuntimeException(e);
			}
			catch (RuntimeException | Error e) {
				c.rollback();
				throw e;
			}
			
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		
	}
	
	
	@Override
	public void invalidarOferta(Long idOferta) throws InputValidationException, InstanceNotFoundException,
			OfertaYaInvalidadaException {
		
		try (Connection c = datos.getConnection() ) {
			
			c.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			c.setAutoCommit(false);
			
			try {
				
				PropertyValidator.validateNotNegativeLong("idOferta", idOferta);
				
				Oferta o = ofertaDao.buscar(c, idOferta);
				
				if (o.isInvalida() ) {
					throw new OfertaYaInvalidadaException(o.getIdOferta());
				}
				
				o.setInvalida(true);
				
				ofertaDao.actualizar(c, o);
				
				List<Reserva> reservas = reservaDao.buscar(c, o.getIdOferta());
				
				if (!reservas.isEmpty() ) {
					reservaDao.actualizar(c, EstadoReserva.ANULADA, o.getIdOferta());
				}
				
				c.commit();
			}
			catch (InstanceNotFoundException e){
				c.commit();
				throw e;
			}
			catch (SQLException e) {
				c.rollback();
				throw new RuntimeException(e);
			}
			catch (RuntimeException | Error e) {
				c.rollback();
				throw e;
			}
			catch (InputValidationException e) {
				c.commit();
				throw e;
			}
			
		}
		catch (SQLException e){
			throw new RuntimeException(e);
		}
		
	}
	
	
	
	@Override
	public void borrarOferta(Long idOferta) throws InputValidationException,InstanceNotFoundException,
			HayReservasException {
		
		PropertyValidator.validateNotNegativeLong("Identificador de Oferta", idOferta);
		
		try (Connection c = datos.getConnection() ) {
			
			try {
				
				c.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				c.setAutoCommit(false);
				
				if(!reservaDao.buscar(c, idOferta).isEmpty() ){
					throw new HayReservasException(idOferta);
				}
				
				ofertaDao.eliminar(c, idOferta);
				
				c.commit();
				
			}
			catch (InstanceNotFoundException e) {
				c.commit();
				throw e;
			}
			catch (SQLException e) {
				c.rollback();
				throw e;
			}
			catch(RuntimeException | Error e) {
				c.rollback();
				throw e;
			}
			
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	
	@Override
	public Oferta buscarOferta(Long idOferta) throws InputValidationException,InstanceNotFoundException {
				
		PropertyValidator.validateNotNegativeLong("Identificador de Oferta", idOferta);
		
		try (Connection c = datos.getConnection() ) {
			return ofertaDao.buscar(c, idOferta);
			
		}
		catch (SQLException e ) {
			throw new RuntimeException(e);
		}
		
	}
	
	
	@Override
	public List<Oferta> buscarOferta (String palabrasClave , Calendar fecha , boolean soloValidas){
		
		try (Connection c = datos.getConnection() ) {										
			return ofertaDao.buscar(c, palabrasClave, fecha , soloValidas);			
			
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
	@Override
	public long reservarOferta(String usuario,String tarjeta,Long idOferta) throws InstanceNotFoundException,
			InputValidationException, OfertaYaReservadaException, FechaReservaExpiradaException {
		
		PropertyValidator.validateMandatoryString("Usuario", usuario);
		PropertyValidator.validateCreditCard(tarjeta);
		PropertyValidator.validateNotNegativeLong("Identificador de Oferta", idOferta);

		try (Connection c = datos.getConnection() ){
			
			try {
				
				c.setAutoCommit(false);
				c.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				
				//buscamos si existe la oferta a reservar -> excepcion en caso de que no haya ninguna
				Oferta o = ofertaDao.buscar(c, idOferta);
				if (o.getFechaLimReserva().before(Calendar.getInstance())){
					throw new FechaReservaExpiradaException(Calendar.getInstance());
				}
				
				List <Reserva> reservas = reservaDao.buscar(c, o.getIdOferta());
				
				for (Reserva r : reservas) {
					if (r.getEmail().equals(usuario) ) {
						throw new OfertaYaReservadaException(idOferta);
					}
				}
				
				Calendar hoy = Calendar.getInstance();												
				
				Reserva r = new Reserva(usuario,tarjeta,idOferta,EstadoReserva.PENDIENTE,hoy,o.getPrecioDescontado());
				
				Reserva vuelta = reservaDao.crear(c, r);
								
				c.commit();
				//devolvemos su codigo para que el cliente pueda reclamarla
				return vuelta.getCodigo();
				
				
			}
			catch (InstanceNotFoundException e) {
				c.commit();
				throw e;
			}
			catch (SQLException e) {
				c.rollback();
				throw e;
			}
			catch (RuntimeException | Error e) {
				c.rollback();
				throw e;
			}
			
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	
	@Override
	public void reclamarReserva(Long codigo) throws InputValidationException,InstanceNotFoundException,
			ReservaYaReclamadaException {
		
		PropertyValidator.validateNotNegativeLong("Codigo de reserva", codigo);
		
		try (Connection c = datos.getConnection() ){
			
			c.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			c.setAutoCommit(false);
			
			try {
				
				Reserva r = reservaDao.buscarID(c, codigo);
				
				if (r.getEstado() == EstadoReserva.RECLAMADA ) {
					throw new ReservaYaReclamadaException(codigo);
				}
				
				Oferta o_asociada = ofertaDao.buscar(c, r.getIdOferta());
				
				if ( o_asociada.isInvalida() ) {
					throw new InputValidationException(" La oferta es invalida, no se puede reservar");
				}
				
				r.setEstado(EstadoReserva.RECLAMADA);
				reservaDao.actualizar(c, r);
				
				c.commit();
				
			}
			catch(InstanceNotFoundException e) {
				c.commit();
				throw e;
			}
			catch (SQLException e) {
				c.rollback();
				throw e;
			}
			catch (RuntimeException | Error e) {
				c.rollback();
				throw e;
			}
			
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Reserva> buscarReserva(Long idOferta) throws InputValidationException{
		
		PropertyValidator.validateNotNegativeLong("Identificador de oferta", idOferta);
		
		try (Connection c = datos.getConnection() ) {
				return reservaDao.buscar(c, idOferta);
				
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
			
		}
		
	}
	
	public List<Reserva> buscarReserva(String usuario, boolean soloValidas) throws InputValidationException {
		
		PropertyValidator.validateMandatoryString("Usuario", usuario);
		
		try (Connection c = datos.getConnection() ) {
			
			try {
				List<Reserva> reservas = reservaDao.buscar(c, usuario, soloValidas);
				
				return reservas;
				
			}
			catch (RuntimeException | Error e) {
				throw e;
			}
			
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
}


