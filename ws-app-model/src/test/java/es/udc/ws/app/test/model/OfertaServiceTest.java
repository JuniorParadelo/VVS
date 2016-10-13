package es.udc.ws.app.test.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import es.udc.ws.app.exceptions.FechaInvalidaException;
import es.udc.ws.app.exceptions.FechaReservaExpiradaException;
import es.udc.ws.app.exceptions.HayReservasException;
import es.udc.ws.app.exceptions.OfertaYaInvalidadaException;
import es.udc.ws.app.exceptions.OfertaYaReservadaException;
import es.udc.ws.app.exceptions.PrecioInvalidoException;
import es.udc.ws.app.exceptions.ReclamacionAnteriorReservaException;
import es.udc.ws.app.exceptions.ReservaYaReclamadaException;
import es.udc.ws.app.model.oferta.Oferta;
import es.udc.ws.app.model.ofertaservice.OfertaService;
import es.udc.ws.app.model.ofertaservice.OfertaServiceFactory;
import es.udc.ws.app.model.reserva.Reserva;
import es.udc.ws.app.model.reserva.SqlReservaDao;
import es.udc.ws.app.model.reserva.SqlReservaDaoFactory;
import es.udc.ws.app.reserva.EstadoReserva;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.sql.SimpleDataSource;
import static es.udc.ws.app.model.util.ConstantesDelModelo.*;





public class OfertaServiceTest {

	private final long ID_OFERTA_INVALIDA = -1;
	private final long ID_OFERTA_VALIDA = 1;
	
	private final String USUARIO = "loki@asgard";
	
	private final String TARJETA_VALIDA = "1234567890123456";
	
	private static OfertaService ofertaService= null;
	private static SqlReservaDao sqlReservaDao= null;
	
	private Calendar fechaLimiteReserva = Calendar.getInstance();
	
	private Calendar fechaLimiteReclamacion = Calendar.getInstance();

	
	
	@BeforeClass
	public static void init() {
		
		DataSource dataSource = new SimpleDataSource();
		
		DataSourceLocator.addDataSource(OFERTA_DATA_SOURCE, dataSource);
		
		ofertaService = OfertaServiceFactory.getService();
		sqlReservaDao = SqlReservaDaoFactory.getDao();
		
	}
	
	private Oferta getValidOferta(String nombre) {
		fechaLimiteReserva.set(Calendar.YEAR, 2020);
		fechaLimiteReserva.set(Calendar.MONTH,Calendar.APRIL);
		fechaLimiteReclamacion.set(Calendar.YEAR,2021);
		
		return new Oferta(nombre, "ES BIEN LA OFERTA", fechaLimiteReserva, fechaLimiteReclamacion, 20, 18, 2, false);
	}
	
	
	private void eliminarReserva(Long codigo) {
		
		DataSource dataSource = DataSourceLocator.getDataSource(OFERTA_DATA_SOURCE);
		
		try (Connection c = dataSource.getConnection() ) {
			
			
			try {
				c.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				c.setAutoCommit(false);
				
				sqlReservaDao.eliminar(c, codigo);
				
				c.commit();
			}
			catch (InputValidationException e) {
				c.commit();
				throw new RuntimeException(e);
			}
			catch (InstanceNotFoundException e) {
				c.commit();
				throw new RuntimeException(e);
			}
			catch (SQLException e) {
				c.rollback();
				throw new RuntimeException(e);
			}
			catch (RuntimeException | Error e) {
				throw e;
			}
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
	
	
	private Reserva buscarPorID(Long codigo) {
		
		DataSource datos = DataSourceLocator.getDataSource(OFERTA_DATA_SOURCE);
		
		try (Connection c = datos.getConnection() ) {
			
			try {
				c.setAutoCommit(false);
				c.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				
				return sqlReservaDao.buscarID(c, codigo);
			}
			catch (InstanceNotFoundException e) {
				c.commit();
				throw new RuntimeException(e);
			}
			catch (InputValidationException e) {
				c.commit();
				throw new RuntimeException(e);
			}
			catch (SQLException e) {
				c.rollback();
				throw new RuntimeException();
			}
			catch (RuntimeException | Error e) {
				c.rollback();
				throw e;
			}
			
			
		}
		catch ( SQLException e ) {
			throw new RuntimeException(e);
		}
		
		
	}
	
	
	private void actualizarReserva(Long codigo,EstadoReserva estado) throws InputValidationException,
	InstanceNotFoundException {
		
		DataSource datos = DataSourceLocator.getDataSource(OFERTA_DATA_SOURCE);

		
		try (Connection c = datos.getConnection() ) {
			
			try  {
				
				c.setAutoCommit(false);
				c.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				
				Reserva r = sqlReservaDao.buscarID(c, codigo);
				
				r.setEstado(estado);
				
				sqlReservaDao.actualizar(c, r);
				
				c.commit();
				
				
			}
			catch (InputValidationException e) {
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
			catch (RuntimeException | Error e)  {
				c.rollback();
				throw e;
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		
		
	}
	
	
	
	
	
	@Test
	public void testCrearBuscarOferta() throws InputValidationException,InstanceNotFoundException, HayReservasException, ReclamacionAnteriorReservaException {
		
		Oferta o = getValidOferta("test crear buscar oferta");
		Oferta oCreada = ofertaService.crearOferta(o);
		Oferta oEncontrada = ofertaService.buscarOferta( oCreada.getIdOferta() );
		
		Assert.assertTrue( oCreada.equals(oEncontrada) );
		
		//limpiamos la BD
		ofertaService.borrarOferta(oCreada.getIdOferta() );
		
	}
	
	@Test(expected = InputValidationException.class)
	public void testCrearOfertaNombreInvalido() throws InputValidationException, ReclamacionAnteriorReservaException {
		
		Oferta o = new Oferta("","LAMENTABLE",fechaLimiteReserva,fechaLimiteReclamacion,20, 18, 2,false);
		
		ofertaService.crearOferta(o);
		
	}
	
	@Test(expected = InputValidationException.class)
	public void testCrearOfertaDescrInvalido() throws InputValidationException, ReclamacionAnteriorReservaException {
		
		Oferta o = new Oferta("JUANA","",fechaLimiteReserva,fechaLimiteReclamacion,20, 18, 2,false);
		
		ofertaService.crearOferta(o);
		
	}
	
	@Test(expected = InputValidationException.class)
	public void testCrearOfertaFechaResAnteriorActual() throws InputValidationException, ReclamacionAnteriorReservaException {
		
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 1900);
		
		Oferta o = new Oferta("JUANA","LAMENTABLE",c,Calendar.getInstance(),20, 18, 2,false);
		
		ofertaService.crearOferta(o);
		
	}
	
	@Test(expected = InputValidationException.class)
	public void testCrearOfertaFechaRecAnteriorActual() throws InputValidationException, ReclamacionAnteriorReservaException {
		
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 1900);
		Oferta o = new Oferta(ID_OFERTA_VALIDA,"JUANA","LAMENTABLE",Calendar.getInstance(),c,20, 18, 2,false);
		
		ofertaService.crearOferta(o);
		
	}
	
	@Test(expected= ReclamacionAnteriorReservaException.class)
	public void testCrearOfertaFechaRecAnteriorFechaRes() throws InputValidationException, ReclamacionAnteriorReservaException {
		
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR,2017);
		
		Oferta o = getValidOferta("crear oferta Rec < Res");
		o.setFechaLimReclamacion(c);
		
		ofertaService.crearOferta(o);
		
		
	}
	
	
	@Test(expected = InputValidationException.class)
	public void testCrearOfertaPrecioRealInvalido() throws InputValidationException, ReclamacionAnteriorReservaException {
		
		Oferta o = new Oferta(ID_OFERTA_VALIDA,"JUANA","LAMENTABLE",Calendar.getInstance(),Calendar.getInstance(),-7, 18, 2,false);
		
		ofertaService.crearOferta(o);
		
	}
	
	@Test(expected = InputValidationException.class)
	public void testCrearOfertaPrecioDescInvalido() throws InputValidationException, ReclamacionAnteriorReservaException {
		
		Oferta o = new Oferta(ID_OFERTA_VALIDA,"JUANA","LAMENTABLE",Calendar.getInstance(),Calendar.getInstance(),20, -8, 2,false);
		
		ofertaService.crearOferta(o);
		
	}
	
	@Test(expected = InputValidationException.class)
	public void testCrearOfertaComisionInvalida() throws InputValidationException, InstanceNotFoundException, ReclamacionAnteriorReservaException {
		
		Oferta o = new Oferta(ID_OFERTA_VALIDA,"JUANA","LAMENTABLE",Calendar.getInstance(),Calendar.getInstance(),20, 30, -7,false);
		
		ofertaService.crearOferta(o);
		
	}
	
	
	// ------------------------ Crear Oferta ----------------------------
	
	
	// ------------------------ Actualizar Oferta -----------------------
	
	@Test
	public void testActualizarOferta() throws InputValidationException,InstanceNotFoundException,
	FechaInvalidaException, PrecioInvalidoException, ReclamacionAnteriorReservaException,
	HayReservasException {
		
		Oferta o = getValidOferta( "actualizar oferta" );
		Oferta oCreada = ofertaService.crearOferta(o);
		Oferta replica = oCreada;
		
		
		replica.setNombre("OFERTA 2");
		ofertaService.actualizarOferta(replica);
		
		oCreada = ofertaService.buscarOferta(oCreada.getIdOferta());
		
		Assert.assertTrue( replica.equals(oCreada) );
		
		ofertaService.borrarOferta(oCreada.getIdOferta());
		
	}
	

	
	@Test(expected = InstanceNotFoundException.class)
	public void testActualizarOfertaInexistente() throws InputValidationException,InstanceNotFoundException,
	FechaInvalidaException, PrecioInvalidoException, ReclamacionAnteriorReservaException {
		
		long id_imposible = 1024265896;
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 2017);
		
		
		Oferta o = new Oferta(id_imposible,"Juan","LAMENTABLE",c,c,20,30,2,true);
		
		ofertaService.actualizarOferta(o);
		
	}
	
	
	@Test(expected = FechaInvalidaException.class)
	public void testActualizarOfertaRetrasarRecConReservas() throws InputValidationException,InstanceNotFoundException, FechaInvalidaException, PrecioInvalidoException,
	OfertaYaReservadaException, ReclamacionAnteriorReservaException,
	FechaReservaExpiradaException, HayReservasException {
		
		Oferta o1 = getValidOferta("test actualizar retrasar rec con reservas");
		
		Calendar fechaMala = Calendar.getInstance();
		fechaMala.set(Calendar.YEAR,2022);
		fechaMala.set(Calendar.MONTH,Calendar.JANUARY);
		o1.setFechaLimReclamacion(fechaMala);
		
		Oferta o = ofertaService.crearOferta(o1);
		
		fechaMala.set(Calendar.YEAR,2021);
		fechaMala.set(Calendar.MONTH,Calendar.JANUARY);
		
		
		Long codigo = ofertaService.reservarOferta(USUARIO, TARJETA_VALIDA, o.getIdOferta());
		
		
		o.setFechaLimReclamacion(fechaMala);
		try {
			ofertaService.actualizarOferta(o);
		}
		catch (FechaInvalidaException e) {
			eliminarReserva(codigo);
			ofertaService.borrarOferta(o.getIdOferta());
			throw new FechaInvalidaException(fechaMala) ;
		}
		
	}
	
	
	@Test(expected = PrecioInvalidoException.class)
	public void testActualizarOfertaAumentarPrecioDescontado() throws InputValidationException,InstanceNotFoundException, FechaInvalidaException, PrecioInvalidoException,
	OfertaYaReservadaException, ReclamacionAnteriorReservaException,
	FechaReservaExpiradaException, HayReservasException {
		
		float precioDescontado = 49;
		
		Oferta o = getValidOferta("test actualizar oferta aumentar precio descontado");

		Oferta o1 = ofertaService.crearOferta(o);

		Long codigo = ofertaService.reservarOferta(USUARIO, TARJETA_VALIDA, o1.getIdOferta());
		o1.setPrecioDescontado(precioDescontado);
		try {
			ofertaService.actualizarOferta(o1);
		}
		catch (PrecioInvalidoException e) {
			eliminarReserva(codigo);
			ofertaService.borrarOferta(o1.getIdOferta());
			throw e;
		}
		
		
	}
	
	// los tests de comprobacion de datos son los mismos que hay en el metodo de crear oferta
	
	
	
	
	
	//--------------------- invalidar oferta ------------------------
	
	
	

	
	@Test
	public void testInvalidarOfertaSinReserva() throws InputValidationException, InstanceNotFoundException,
	OfertaYaInvalidadaException, ReclamacionAnteriorReservaException, HayReservasException{
		
		Oferta o = getValidOferta("invalidar oferta sin reserva");
		
		Oferta oCreada1 = ofertaService.crearOferta(o);
		
		ofertaService.invalidarOferta(oCreada1.getIdOferta());
		
		Oferta o_busq = ofertaService.buscarOferta(oCreada1.getIdOferta());
		
		Assert.assertEquals(o_busq.isInvalida(), true);
		
		ofertaService.borrarOferta(o_busq.getIdOferta());
		
	}
	
	@Test
	public void testInvalidarOfertaConReserva() throws InputValidationException, InstanceNotFoundException,
	OfertaYaReservadaException, OfertaYaInvalidadaException, ReclamacionAnteriorReservaException,
	FechaReservaExpiradaException, HayReservasException {
		
		Oferta o = getValidOferta("invalidar oferta con reserva");
		
		Oferta oCreada1 = ofertaService.crearOferta(o);
		
		Long codReserva = ofertaService.reservarOferta(USUARIO, TARJETA_VALIDA, oCreada1.getIdOferta() );
		
		ofertaService.invalidarOferta(oCreada1.getIdOferta());
		
		oCreada1 = ofertaService.buscarOferta(oCreada1.getIdOferta());
		
		Reserva resAnulada = buscarPorID(codReserva);
		
		Assert.assertEquals(resAnulada.getEstado(), EstadoReserva.ANULADA);
		Assert.assertEquals(oCreada1.isInvalida(), true);
		
		eliminarReserva(resAnulada.getCodigo());
		ofertaService.borrarOferta(oCreada1.getIdOferta());
	}
	
	
	@Test(expected=InputValidationException.class)
	public void testInvalidarOfertaConReservaErrorEntrada() throws InputValidationException, InstanceNotFoundException,
	OfertaYaInvalidadaException, ReclamacionAnteriorReservaException, HayReservasException {
		
		Oferta o = getValidOferta("excepcion invalidar input");
		
		Oferta oCreada1 = ofertaService.crearOferta(o);
		Long id_anterior = oCreada1.getIdOferta();
		oCreada1.setIdOferta(ID_OFERTA_INVALIDA);
		
		try {
			ofertaService.invalidarOferta(oCreada1.getIdOferta());
		}
		catch (InputValidationException e) {
			ofertaService.borrarOferta(id_anterior);
			throw e;
		}
	}
	
	@Test(expected=InstanceNotFoundException.class)
	public void testInvalidarOfertaConReservaErrorNoExiste() throws InputValidationException, InstanceNotFoundException, OfertaYaInvalidadaException {
		
		Oferta o = getValidOferta("excpecion invalidar inexistente");
		long id_imposible = 243334565;
		
		o.setIdOferta(id_imposible);
		
		ofertaService.invalidarOferta(o.getIdOferta());
		
		
	}
	
	
	@Test(expected=OfertaYaInvalidadaException.class)
	public void testInvalidarOfertaYaInvalidada() throws InputValidationException,InstanceNotFoundException,
	OfertaYaInvalidadaException, ReclamacionAnteriorReservaException, HayReservasException{
	
		Oferta o = getValidOferta("test inv off ya inv");
		
		Oferta oCreada = ofertaService.crearOferta(o);
		
		ofertaService.invalidarOferta(oCreada.getIdOferta());
		
		try {
			ofertaService.invalidarOferta(oCreada.getIdOferta());
		}
		catch (OfertaYaInvalidadaException e) {
			ofertaService.borrarOferta(oCreada.getIdOferta());
			throw e;
		}
		
	}
	
	
	// ------------------------ borrar Oferta ---------------------------------------
	// el borrado normal ya esta probado en los anteriores tests al eliminar los datos
	
	
	
	@Test(expected=InstanceNotFoundException.class)
	public void testBorrarOferta() throws InputValidationException,InstanceNotFoundException,
	ReclamacionAnteriorReservaException, HayReservasException {
		Oferta o = getValidOferta("borrar oferta inexistente");
		Oferta oCreada = ofertaService.crearOferta(o);
		Long id = oCreada.getIdOferta();
		
		ofertaService.borrarOferta(oCreada.getIdOferta());
		
		ofertaService.buscarOferta(id);
		
	}
	
	@Test(expected = InstanceNotFoundException.class)
	public void testBorrarOfertaInexistente() throws InputValidationException,InstanceNotFoundException, HayReservasException {
		long id_imposible = 1024265896;
		
		ofertaService.borrarOferta(id_imposible);
	}
	
	@Test(expected = InputValidationException.class)
	public void testBorrarOfertaIDInvalido() throws InputValidationException,InstanceNotFoundException,
	HayReservasException {
		ofertaService.borrarOferta(ID_OFERTA_INVALIDA);
	}
	
	@Test(expected=HayReservasException.class) 
	public void testBorrarOfertaConReservas() throws HayReservasException,
	InputValidationException, ReclamacionAnteriorReservaException,
	InstanceNotFoundException, OfertaYaReservadaException, FechaReservaExpiradaException {
		
		Oferta o = getValidOferta("borrar oferta con reservas");
		
		Oferta oCreada = ofertaService.crearOferta(o);
		
		Long codReserva = ofertaService.reservarOferta(USUARIO, TARJETA_VALIDA, oCreada.getIdOferta());
		
		try {
			ofertaService.borrarOferta(oCreada.getIdOferta());
			
		}
		catch (HayReservasException e) {
			eliminarReserva(codReserva);
			ofertaService.borrarOferta(oCreada.getIdOferta());
			throw e;
		}
		
		
	}
	
	
	// --------------------- reservar oferta -----------------
	
	
	@Test
	public void testReservarOferta() throws InstanceNotFoundException,InputValidationException,
	OfertaYaReservadaException, ReclamacionAnteriorReservaException, FechaReservaExpiradaException, HayReservasException {
		
		Oferta o = getValidOferta("Ejemplo de reserva");
		
		Oferta oCreada = ofertaService.crearOferta(o);
		
		Long codReservaCreada = ofertaService.reservarOferta(USUARIO, TARJETA_VALIDA, oCreada.getIdOferta());
		
		Reserva rEncontrada = buscarPorID(codReservaCreada);
		
		Assert.assertEquals(rEncontrada.getIdOferta(), oCreada.getIdOferta());
		
		eliminarReserva(rEncontrada.getCodigo());
		ofertaService.borrarOferta(oCreada.getIdOferta());
		
	}
	
	
	@Test(expected = InputValidationException.class)
	public void testReservarOfertaUsuarioInvalido() throws InputValidationException,
	InstanceNotFoundException, OfertaYaReservadaException, FechaReservaExpiradaException {
		
		ofertaService.reservarOferta( "",TARJETA_VALIDA,ID_OFERTA_VALIDA);
	}
	
	@Test(expected = InputValidationException.class)
	public void testReservarOfertaTarjetaInvalido() throws InputValidationException,InstanceNotFoundException, OfertaYaReservadaException,
	FechaReservaExpiradaException {
		ofertaService.reservarOferta( USUARIO,"",ID_OFERTA_VALIDA);
	}
	
	@Test(expected = InputValidationException.class)
	public void testReservarOfertaIDOfertaInvalido() throws InputValidationException,InstanceNotFoundException,
	OfertaYaReservadaException, FechaReservaExpiradaException {
		ofertaService.reservarOferta( USUARIO,TARJETA_VALIDA,ID_OFERTA_INVALIDA);
	}
	
	@Test(expected = InstanceNotFoundException.class)
	public void testReservarOfertaInexistente() throws InputValidationException,InstanceNotFoundException,
	OfertaYaReservadaException, FechaReservaExpiradaException {
		long id_imposible = 1024265896;
		ofertaService.reservarOferta( USUARIO,TARJETA_VALIDA,id_imposible);
	}
	
	@Test(expected = OfertaYaReservadaException.class)
	public void testReservarDosVeces() throws InputValidationException,InstanceNotFoundException,
	OfertaYaReservadaException, 
	ReclamacionAnteriorReservaException, FechaReservaExpiradaException, HayReservasException {
		
		Oferta o = getValidOferta("reservar dos veces");
		
		Oferta o1= ofertaService.crearOferta(o);
		Long codigo = ofertaService.reservarOferta( USUARIO,TARJETA_VALIDA,o1.getIdOferta());
		
		try {
			ofertaService.reservarOferta( USUARIO,TARJETA_VALIDA,o1.getIdOferta());
		}
		catch (OfertaYaReservadaException e) {
			eliminarReserva(codigo);
			ofertaService.borrarOferta(o1.getIdOferta());
			throw e;
		}
	}
	
	
//	@Test(expected=FechaReservaExpiradaException.class)
//	public void testReservarConFechaExpirada() throws FechaReservaExpiradaException {
//		
//		
//		//¿? como probar esto
//		
//	}
	
	
	
	//------------------------- reclamar Reserva -----------------------------------
	
	
	@Test
	public void testReclamarReserva() throws InputValidationException,InstanceNotFoundException,
	OfertaYaReservadaException, ReservaYaReclamadaException, ReclamacionAnteriorReservaException,
	FechaReservaExpiradaException, HayReservasException{
		
		Oferta o = getValidOferta("reclamar reserva");
		
		Oferta oCreada = ofertaService.crearOferta(o);
		
		Long codReservaCreada = ofertaService.reservarOferta(USUARIO, TARJETA_VALIDA, oCreada.getIdOferta());
		
		Reserva rEncontrada = buscarPorID(codReservaCreada);
		
		ofertaService.reclamarReserva(rEncontrada.getCodigo());
		
		Reserva rReclamada = buscarPorID(rEncontrada.getCodigo());
		
		Assert.assertEquals(rReclamada.getEmail(),rEncontrada.getEmail());
		Assert.assertEquals(rReclamada.getEstado(), EstadoReserva.RECLAMADA);
		
		eliminarReserva(rEncontrada.getCodigo());
		ofertaService.borrarOferta(oCreada.getIdOferta());
		

	}
	
	@Test(expected = InstanceNotFoundException.class)
	public void testReclamarReservaInexistente() throws InputValidationException,InstanceNotFoundException, ReservaYaReclamadaException {
		long codigo_imposible = 1024265896;
		ofertaService.reclamarReserva(codigo_imposible);
	}
	
	@Test(expected = InputValidationException.class)
	public void testReclamarReservaIDInvalido() throws InputValidationException,InstanceNotFoundException, ReservaYaReclamadaException {
		
		ofertaService.reclamarReserva(ID_OFERTA_INVALIDA);
	}
	
	@Test(expected = ReservaYaReclamadaException.class)
	public void testReclamarDosVeces() throws InputValidationException,InstanceNotFoundException,
	ReservaYaReclamadaException, OfertaYaReservadaException, ReclamacionAnteriorReservaException,
	FechaReservaExpiradaException, HayReservasException {
		
		Oferta o = getValidOferta("reclamo dos veces");
		
		Oferta o1 = ofertaService.crearOferta(o);
		
		Long codigo = ofertaService.reservarOferta(USUARIO, TARJETA_VALIDA, o1.getIdOferta());
		
		try {
			ofertaService.reclamarReserva(codigo);
			ofertaService.reclamarReserva(codigo);
		}
		catch (ReservaYaReclamadaException e) {
			eliminarReserva(codigo);
			ofertaService.borrarOferta(o1.getIdOferta());
			throw e;
		}

	}
	
	
	// ----------------------------- buscar oferta ------------------------------
	
	

	@Test
	public void testBuscarOfertaPorId() throws InputValidationException,InstanceNotFoundException,
	ReclamacionAnteriorReservaException, HayReservasException {
		
		Oferta o = getValidOferta("buscar oferta x id");
		
		Oferta oCreada = ofertaService.crearOferta(o);
		
		Oferta oBuscada = ofertaService.buscarOferta(oCreada.getIdOferta());
		
		Assert.assertEquals(oCreada, oBuscada);
		
		ofertaService.borrarOferta(oCreada.getIdOferta());
	}
	
	
	@Test(expected=InputValidationException.class)
	public void testBuscarOfertaPorIDInputError() throws InputValidationException, InstanceNotFoundException {
		
		
		ofertaService.buscarOferta(ID_OFERTA_INVALIDA);		
	}
	
	@Test(expected=InstanceNotFoundException.class)
	public void testBuscarOfertaPorIDInstanceError() throws InputValidationException, InstanceNotFoundException {
		
		
		ofertaService.buscarOferta((long) 846164);		
	}
	
	
	
							//------ buscar por palabras clave
	
	
	@Test
	public void testBuscarOfertasPClave() throws InputValidationException, InstanceNotFoundException, HayReservasException,
	ReclamacionAnteriorReservaException, FechaInvalidaException, PrecioInvalidoException {
		
		Oferta o1 = getValidOferta("oferta1");
		Oferta o2 = getValidOferta("oferta2");
		Oferta o3 = getValidOferta("oferta3");
		
		o1.setDescripcion("hoal");
		o2.setDescripcion("koala");
		o3.setDescripcion("mira");
		
		Oferta oCreada1 = ofertaService.crearOferta(o1);
		Oferta oCreada2 = ofertaService.crearOferta(o2);
		Oferta oCreada3 = ofertaService.crearOferta(o3);
		
		
		Calendar despues = Calendar.getInstance();
		despues.set(Calendar.YEAR,2019);
		
		List<Oferta> ofertas = ofertaService.buscarOferta("oa", despues, false);
		
		Assert.assertEquals(ofertas.size(), 2);
		
		oCreada1.setInvalida(true);
		ofertaService.actualizarOferta(oCreada1);
		
		List<Oferta> ofertas2 = ofertaService.buscarOferta("oa", despues, true);
		
		Calendar c = (Calendar) fechaLimiteReserva.clone();
		c.set(Calendar.YEAR,2017);
		
		Assert.assertEquals(ofertas2.size(), 1);
		
		List<Oferta> ofertas3 = ofertaService.buscarOferta("i", c, false);
		
		Assert.assertEquals(ofertas3.size(),1);
		
		ofertaService.borrarOferta(oCreada1.getIdOferta());
		ofertaService.borrarOferta(oCreada2.getIdOferta());
		ofertaService.borrarOferta(oCreada3.getIdOferta());
		
	}
	
	
	
	
	// -------------------- buscarReservas -----------------------------------
	
	
	@Test
	public void testBuscarReservaIdOferta() throws InputValidationException,
	ReclamacionAnteriorReservaException, InstanceNotFoundException, OfertaYaReservadaException,
	FechaReservaExpiradaException, HayReservasException {
		
		Oferta o = getValidOferta("buscar reservas id");
		
		Oferta oCreada = ofertaService.crearOferta(o);
		
		Long codReserva = ofertaService.reservarOferta(USUARIO, TARJETA_VALIDA, oCreada.getIdOferta());
		Long codReserva2 = ofertaService.reservarOferta("jvsjbvjfd",TARJETA_VALIDA,oCreada.getIdOferta());
		
		List<Reserva> r = ofertaService.buscarReserva(oCreada.getIdOferta());
		
		Assert.assertEquals(r.size(), 2);
		
		eliminarReserva(codReserva);
		eliminarReserva(codReserva2);
		
		ofertaService.borrarOferta(oCreada.getIdOferta());
		
		
	}
	
	@Test(expected=InputValidationException.class)
	public void testBuscarReservaIdOfertaConInput() throws InputValidationException, InstanceNotFoundException {
		
		ofertaService.buscarOferta( (long) -1);
	}
	
	
	
	@Test
	public void testBuscarReservaPorUsuario() throws InputValidationException, ReclamacionAnteriorReservaException,
	InstanceNotFoundException, OfertaYaReservadaException, FechaReservaExpiradaException, HayReservasException {
		
		Oferta o = getValidOferta("buscar reserva x usuario");
		Oferta o2 = getValidOferta("buscar reserva x usuario 2");
		
		String USUARIO2 = "juanm@olosdineros";
		
		Oferta oCreada = ofertaService.crearOferta(o);
		Oferta oCreada2 = ofertaService.crearOferta(o2);
		
		Long codReserva1 = ofertaService.reservarOferta(USUARIO, TARJETA_VALIDA, oCreada.getIdOferta());
		Long codŔeserva2 = ofertaService.reservarOferta(USUARIO2, TARJETA_VALIDA, oCreada.getIdOferta());
		Long codReserva3 = ofertaService.reservarOferta(USUARIO2, TARJETA_VALIDA, oCreada2.getIdOferta());
		
		Reserva r1 = buscarPorID(codReserva1);
		Reserva r2 = buscarPorID(codŔeserva2);
		Reserva r3 = buscarPorID(codReserva3);
		
		List<Reserva> reservas = ofertaService.buscarReserva(USUARIO2, false);
		
		Assert.assertEquals(2, reservas.size());
		
		//comprobamos caso de buscar por usuario
		
		actualizarReserva(codReserva1, EstadoReserva.ANULADA);
		
		reservas = ofertaService.buscarReserva(USUARIO, true);
		
		Assert.assertEquals(0,reservas.size());
		
		//comprobamos caso de buscar soloValidas
		
		
		eliminarReserva(codReserva1);
		eliminarReserva(codŔeserva2);
		eliminarReserva(codReserva3);
		
		ofertaService.borrarOferta(oCreada.getIdOferta());
		ofertaService.borrarOferta(oCreada2.getIdOferta());
		
		
	}
	
	
	@Test(expected= InputValidationException.class)
	public void testBuscarReservasPorUsuario() throws InputValidationException{
		
		ofertaService.buscarReserva("", true);
		
		
	}
	
	
	
	
}
