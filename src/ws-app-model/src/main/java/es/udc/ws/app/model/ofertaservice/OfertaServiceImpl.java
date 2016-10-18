package es.udc.ws.app.model.ofertaservice;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.sql.DataSource;

import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.app.enums.EstadoOferta;
import es.udc.ws.app.enums.EstadoReserva;
import es.udc.ws.app.exceptions.FechaInvalidaException;
import es.udc.ws.app.exceptions.OfertaExpirationException;
import es.udc.ws.app.exceptions.OfertaInvalidaException;
import es.udc.ws.app.exceptions.OfertaReservedByUserException;
import es.udc.ws.app.exceptions.OfertaReservedException;
import es.udc.ws.app.exceptions.PrecioDescontadoException;
import es.udc.ws.app.exceptions.ReservaExpirationException;
import es.udc.ws.app.exceptions.ReservaNoValidaException;
import es.udc.ws.app.model.oferta.Oferta;
import es.udc.ws.app.model.oferta.SqlOfertaDao;
import es.udc.ws.app.model.oferta.SqlOfertaDaoFactory;
import es.udc.ws.app.model.reserva.Reserva;
import es.udc.ws.app.model.reserva.SqlReservaDao;
import es.udc.ws.app.model.reserva.SqlReservaDaoFactory;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.app.validation.PropertyValidator;
import static es.udc.ws.app.model.util.ModelConstants.OFERTA_DATA_SOURCE;
import static es.udc.ws.app.model.util.ModelConstants.PRECIO_MAX;
import static es.udc.ws.app.model.util.ModelConstants.COMISION_MAX;

public class OfertaServiceImpl implements OfertaService{

    private DataSource dataSource;
    private SqlOfertaDao ofertaDao = null;
    private SqlReservaDao reservaDao = null;

    public OfertaServiceImpl() {
        dataSource = DataSourceLocator.getDataSource(OFERTA_DATA_SOURCE);
        ofertaDao = SqlOfertaDaoFactory.getDao();
        reservaDao = SqlReservaDaoFactory.getDao();
    }
    
    private void validarOferta(Oferta oferta) throws InputValidationException {

        PropertyValidator.validateMandatoryString("nombre", oferta.getNombre());
        PropertyValidator.validateMandatoryString("descripcion", oferta.getDescripcion());
        PropertyValidator.validateFutureDate("fechaReservar",oferta.getFechaReservar());
        PropertyValidator.validateDouble("precioReal", oferta.getPrecioReal(), 0, PRECIO_MAX);        
        PropertyValidator.validateDouble("precioDescontado", oferta.getPrecioDescontado(), 0, PRECIO_MAX);              
        PropertyValidator.validateDouble("comision", oferta.getComision(), 0, COMISION_MAX);
        PropertyValidator.validateState("estado", oferta.getEstado());       
        
    }
    
    
    public Oferta añadirOferta(Oferta oferta) throws InputValidationException {

        validarOferta(oferta);

        try (Connection connection = dataSource.getConnection()) {

            try {

                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Do work. */
                oferta.setEstado(EstadoOferta.VALIDA);
                Oferta nuevaOferta = ofertaDao.create(connection, oferta);

                /* Commit. */
                connection.commit();

                return nuevaOferta;

            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    
    public void comprobarActualizacionValida(Oferta oferta) throws InstanceNotFoundException, FechaInvalidaException, PrecioDescontadoException {
    	
    	try (Connection connection = dataSource.getConnection()) {

                Oferta viejaOferta = ofertaDao.find(connection, oferta.getOfertaId());
                if (oferta.getFechaReclamar().before(viejaOferta.getFechaReclamar())) {
                	throw new FechaInvalidaException();
                }
                
                if (oferta.getPrecioDescontado() > viejaOferta.getPrecioDescontado()) {
                	throw new PrecioDescontadoException(oferta.getPrecioDescontado());
                }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void actualizarOferta(Oferta oferta) throws InputValidationException, InstanceNotFoundException, FechaInvalidaException, PrecioDescontadoException {

        validarOferta(oferta);

        try (Connection connection = dataSource.getConnection()) {

            try {
            	
            	if (ofertaDao.isReserved(connection, oferta.getOfertaId())) {
            		comprobarActualizacionValida(oferta);
            	}
        

                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Do work. */
                ofertaDao.update(connection, oferta);

                /* Commit. */
                connection.commit();

            } catch (InstanceNotFoundException e) {
                connection.commit();
                throw e;
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    
    @Override
    public void eliminarOferta (Long ofertaId) throws InstanceNotFoundException, OfertaReservedException {

        try (Connection connection = dataSource.getConnection()) {
        	
        	if (ofertaDao.isReserved(connection, ofertaId)) {
        		throw new OfertaReservedException(ofertaId);
        	}

            try {

                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Do work. */
                ofertaDao.remove(connection, ofertaId);

                /* Commit. */
                connection.commit();

            } catch (InstanceNotFoundException e) {
                connection.commit();
                throw e;
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    
    @Override
    public Oferta buscarOferta (Long ofertaId) throws InstanceNotFoundException {

        try (Connection connection = dataSource.getConnection()) {
            return ofertaDao.find(connection, ofertaId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    
    @Override
    public List<Oferta> buscarOfertas (String keywords, Boolean estado, Calendar fecha) {

        try (Connection connection = dataSource.getConnection()) {
        	if (estado == null || estado == false)
        		return ofertaDao.findByKeywords(connection, keywords, null, fecha);
        	else
        		return ofertaDao.findByKeywords(connection, keywords, EstadoOferta.VALIDA, fecha);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public Reserva reservarOferta (Long ofertaId, String email, String tarjeta) throws InstanceNotFoundException, InputValidationException, OfertaExpirationException, OfertaInvalidaException, OfertaReservedByUserException {

        PropertyValidator.validateCreditCard(tarjeta);

        //TODO Cada usuario sólo puede reservar una vez cada oferta
        try (Connection connection = dataSource.getConnection()) {

            try {

                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);
                
                if (reservaDao.isReservedByUser(connection, ofertaId, email)) {
                	throw new OfertaReservedByUserException(ofertaId, email);
                }

                Oferta oferta = ofertaDao.find(connection, ofertaId);                
                Calendar fechaActual = Calendar.getInstance();
                Calendar ofertaFechaReservar = oferta.getFechaReservar();
                
                if (!oferta.getEstado().equals(EstadoOferta.VALIDA)) {
                	throw new OfertaInvalidaException(oferta.getOfertaId());
                }
                
                if (fechaActual.after(ofertaFechaReservar)) {
                	oferta.setEstado(EstadoOferta.INVALIDA);
                	ofertaDao.update(connection, oferta);
                	throw new OfertaExpirationException(oferta.getOfertaId(), oferta.getFechaReservar());
                }
                
        		Random numeroAleatorio = new Random();
        		int dia = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        		int codigo = (numeroAleatorio.nextInt(1000)*dia);
                
                
                Reserva reserva = reservaDao.create(connection, new Reserva(ofertaId, email, tarjeta,  fechaActual, EstadoReserva.VALIDA, codigo, oferta.getPrecioDescontado()));

                /* Commit. */
                connection.commit();

                return reserva;

            } catch (InstanceNotFoundException e) {
                connection.commit();
                throw e;
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    
    @Override
    public Reserva buscarReserva (Long reservaId) throws InstanceNotFoundException {

        try (Connection connection = dataSource.getConnection()) {

            return reservaDao.find(connection, reservaId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    
    @Override
    public void invalidarOferta (Long ofertaId) throws InstanceNotFoundException, InputValidationException, FechaInvalidaException, PrecioDescontadoException, OfertaInvalidaException {
    	
    	Oferta oferta = buscarOferta(ofertaId);	
    	if (oferta.getEstado() == EstadoOferta.INVALIDA) {
    		throw new OfertaInvalidaException(ofertaId);
    	}
    	oferta.setEstado(EstadoOferta.INVALIDA);
    	actualizarOferta(oferta);
    	
    	
    	try (Connection connection = dataSource.getConnection()) {
    	
	    	//Invalidar reservas relacionadas
    		ofertaDao.cancelReservations(connection, ofertaId);
	    	
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	    	
    	
    }
    
    @Override
    public List<Reserva> buscarReservasDeUnaOferta (Long ofertaId) throws InstanceNotFoundException {
    	
        try (Connection connection = dataSource.getConnection()) {

            return reservaDao.findByOfertaId(connection, ofertaId);          

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    	
    }
    
    @Override
    public List<Reserva> buscarReservasDeUnUsuario (String email, boolean validas) {
    	
    	try (Connection connection = dataSource.getConnection()) {

            return reservaDao.findByUser(connection, email, validas);          

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    	
    }
    
    @Override
    public void reclamarReserva (int codigo) throws InstanceNotFoundException, ReservaNoValidaException, ReservaExpirationException {
    	
    	try (Connection connection = dataSource.getConnection()) {

            Reserva reserva = reservaDao.findByCode(connection, codigo);
            
			
			Calendar fechaActual = Calendar.getInstance();
			
			Oferta oferta = ofertaDao.find(connection, reserva.getOfertaId());
			Calendar fechaLimite = oferta.getFechaReclamar();
			//Calendar fechaLimite = reserva.getFechaLimite();
						
			if (fechaActual.after(fechaLimite)){
				throw new ReservaExpirationException(reserva.getReservaId(), fechaLimite);
			}
            
            if (!reserva.getEstado().equals(EstadoReserva.VALIDA)) {
            	//Si la reserva ya está reservada o ha sido anulada, se lanza la excepcion
            	throw new ReservaNoValidaException(reserva.getReservaId());
            }
            else {
            	//Si la reserva está disponible se cambia el estado a "Reclamada"
            	reserva.setEstado(EstadoReserva.RECLAMADA);
            	reservaDao.update(connection, reserva);
            }
            

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    	
    }
	
}
