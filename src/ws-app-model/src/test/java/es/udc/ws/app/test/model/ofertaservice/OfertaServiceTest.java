package es.udc.ws.app.test.model.ofertaservice;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.Test;

import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.app.exceptions.FechaInvalidaException;
import es.udc.ws.app.exceptions.OfertaExpirationException;
import es.udc.ws.app.exceptions.OfertaInvalidaException;
import es.udc.ws.app.exceptions.OfertaReservedByUserException;
import es.udc.ws.app.exceptions.OfertaReservedException;
import es.udc.ws.app.exceptions.PrecioDescontadoException;
import es.udc.ws.app.exceptions.ReservaExpirationException;
import es.udc.ws.app.exceptions.ReservaNoValidaException;
import es.udc.ws.app.model.oferta.Oferta;
import es.udc.ws.app.model.ofertaservice.OfertaService;
import es.udc.ws.app.model.ofertaservice.OfertaServiceFactory;
import es.udc.ws.app.model.reserva.Reserva;
import es.udc.ws.app.model.reserva.SqlReservaDao;
import es.udc.ws.app.model.reserva.SqlReservaDaoFactory;
import es.udc.ws.app.enums.EstadoOferta;
import es.udc.ws.app.enums.EstadoReserva;
import es.udc.ws.util.sql.DataSourceLocator;
import static es.udc.ws.app.model.util.ModelConstants.OFERTA_DATA_SOURCE;
import static es.udc.ws.app.model.util.ModelConstants.PRECIO_MAX;
import static es.udc.ws.app.model.util.ModelConstants.COMISION_MAX;
import static es.udc.ws.app.model.util.ModelConstants.LIMITE_DIAS_RESERVA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import es.udc.ws.util.sql.SimpleDataSource;

public class OfertaServiceTest {

	private final long ID_OFERTA_NO_EXISTENTE = -1;
	private final long ID_RESERVA_NO_EXISTENTE = -1;
	private final String EMAIL_USUARIO_1 = "ws-user1@udc.es";
	private final String EMAIL_USUARIO_2 = "ws-user2@udc.es";
	private final String EMAIL_USUARIO_3 = "ws-user3@udc.es";
	private final String EMAIL_USUARIO_4 = "ws-user4@udc.es";
	private final String EMAIL_USUARIO_5 = "ws-user5@udc.es";

	private final String TARJETA_CREDITO_VALIDA = "1234567890123456";
	private final String TARJETA_CREDITO_INVALIDA = "";

	private static OfertaService ofertaService = null;

	private static SqlReservaDao reservaDao = null;
	
	@BeforeClass
	public static void init() {

		/*
		 * Create a simple data source and add it to "DataSourceLocator" (this
		 * is needed to test "es.udc.ws.movies.model.movieservice.MovieService"
		 */
		DataSource dataSource = new SimpleDataSource();

		/* Add "dataSource" to "DataSourceLocator". */
		DataSourceLocator.addDataSource(OFERTA_DATA_SOURCE, dataSource);
		
		/* Registrar el datasource para las pruebas de integración, para la ejecución normal
		 * de la app no es necesario
		 */

		ofertaService = OfertaServiceFactory.getService();

		reservaDao = SqlReservaDaoFactory.getDao();
		
	}
	
	private Oferta getOfertaValida(String nombre) {
		Calendar fechaReservar = Calendar.getInstance();
		fechaReservar.add(Calendar.DAY_OF_YEAR, 10);
		
		Calendar fechaReclamar = fechaReservar;
		fechaReclamar.add(Calendar.DAY_OF_YEAR, LIMITE_DIAS_RESERVA);
		return new Oferta(nombre, "Descripción", fechaReservar, fechaReclamar, 80F, 50F, 0.15F, EstadoOferta.VALIDA);
	}

	private Oferta getOfertaValida() {
		return getOfertaValida("Nombre de oferta");
	}

	private Oferta crearOferta (Oferta oferta) {

		Oferta nuevaOferta = null;
		try {
			nuevaOferta = ofertaService.añadirOferta(oferta);
		} catch (InputValidationException e) {
			throw new RuntimeException(e);
		}
		return nuevaOferta;

	}

	private void eliminarOferta(Long ofertaId) throws OfertaReservedException {
		
		try {
			ofertaService.eliminarOferta(ofertaId);
		} catch (InstanceNotFoundException e) {
			throw new RuntimeException(e);
		}

	}

	private void eliminarReserva(Long reservaId) {
		
		DataSource dataSource = DataSourceLocator.getDataSource(OFERTA_DATA_SOURCE);
		
		try (Connection connection = dataSource.getConnection()) {

			try {
	
				/* Prepare connection. */
				connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				connection.setAutoCommit(false);
	
				/* Do work. */
				reservaDao.remove(connection, reservaId);
				
				/* Commit. */
				connection.commit();
	
			} catch (InstanceNotFoundException e) {
				connection.commit();
				throw new RuntimeException(e);
			} catch (SQLException e) {
				connection.rollback();
				throw new RuntimeException(e);
			} catch (RuntimeException|Error e) {
				connection.rollback();
				throw e;
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	private void actualizarReserva(Reserva reserva) throws ReservaExpirationException {
		
		DataSource dataSource = DataSourceLocator.getDataSource(OFERTA_DATA_SOURCE);
		
		try (Connection connection = dataSource.getConnection()) {

			try {
						
				/* Prepare connection. */
				connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				connection.setAutoCommit(false);
	
				/* Do work. */
				reservaDao.update(connection, reserva);
				
				/* Commit. */
				connection.commit();
	
			} catch (InstanceNotFoundException e) {
				connection.commit();
				throw new RuntimeException(e);
			} catch (SQLException e) {
				connection.rollback();
				throw new RuntimeException(e);
			} catch (RuntimeException|Error e) {
				connection.rollback();
				throw e;
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
	@Test
	public void testLimpiarBaseDeDatos() throws InstanceNotFoundException, OfertaReservedException {
		limpiarBaseDeDatos();
	}
	
	
	@Test
	public void testAñadirBuscarOferta() throws InputValidationException, InstanceNotFoundException, OfertaReservedException {

		Oferta oferta = getOfertaValida();
		Oferta nuevaOferta = null;

		nuevaOferta = ofertaService.añadirOferta(oferta);
		Oferta ofertaEncontrada = ofertaService.buscarOferta(nuevaOferta.getOfertaId());
	
		assertEquals(nuevaOferta, ofertaEncontrada);

		// Clear Database
		eliminarOferta(nuevaOferta.getOfertaId());

	}
	
	@Test
	public void testAñadirOfertaInvalida() throws OfertaReservedException {

		Oferta oferta = getOfertaValida();
		Oferta nuevaOferta = null;
		boolean exceptionCatched = false;

		try {
			// Comprobar oferta nombre no nulo
			oferta.setNombre(null);
			try {
				nuevaOferta = ofertaService.añadirOferta(oferta);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Comprobar nombre no vacío
			exceptionCatched = false;
			oferta = getOfertaValida();
			oferta.setNombre("");
			try {
				nuevaOferta = ofertaService.añadirOferta(oferta);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Comprobar precio real válido
			exceptionCatched = false;
			oferta = getOfertaValida();
			oferta.setPrecioReal((short) -1);
			try {
				nuevaOferta = ofertaService.añadirOferta(oferta);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Comprobar precio real <= PRECIO_MAX
			exceptionCatched = false;
			oferta = getOfertaValida();
			oferta.setPrecioReal((short) (PRECIO_MAX + 1));
			try {
				nuevaOferta = ofertaService.añadirOferta(oferta);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Comprobar descripción no nula
			exceptionCatched = false;
			oferta = getOfertaValida();
			oferta.setDescripcion(null);
			try {
				nuevaOferta = ofertaService.añadirOferta(oferta);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Comprobar descripción no vacía
			exceptionCatched = false;
			oferta = getOfertaValida();
			oferta.setDescripcion("");
			try {
				nuevaOferta = ofertaService.añadirOferta(oferta);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Comprobar precio descontado >= 0
			exceptionCatched = false;
			oferta = getOfertaValida();
			oferta.setPrecioDescontado((short) -1);
			try {
				nuevaOferta = ofertaService.añadirOferta(oferta);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Comprobar precio descontado <= PRECIO_MAX
			exceptionCatched = false;
			oferta = getOfertaValida();
			oferta.setPrecioDescontado((short) (PRECIO_MAX + 1));
			try {
				nuevaOferta = ofertaService.añadirOferta(oferta);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);
			
			// Comprobar comision >= 0
			exceptionCatched = false;
			oferta = getOfertaValida();
			oferta.setComision((short) (-1));
			try {
				nuevaOferta = ofertaService.añadirOferta(oferta);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);
			
			// Comprobar comision <= COMISION_MAX
			exceptionCatched = false;
			oferta = getOfertaValida();
			oferta.setComision((short) (COMISION_MAX + 1));
			try {
				nuevaOferta = ofertaService.añadirOferta(oferta);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);
			
			// Comprobar estado válido
			exceptionCatched = false;
			oferta = getOfertaValida();
			oferta.setEstado(EstadoOferta.ERRONEO);
			try {
				nuevaOferta = ofertaService.añadirOferta(oferta);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Comprobar fecha límite no nula
			exceptionCatched = false;
			oferta = getOfertaValida();
			oferta.setFechaReservar(null);
			try {
				nuevaOferta = ofertaService.añadirOferta(oferta);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Comprobar fecha límite como fecha futura
			exceptionCatched = false;
			oferta = getOfertaValida();
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, 1);
			oferta.setFechaReservar(calendar);
			try {
				nuevaOferta = ofertaService.añadirOferta(oferta);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

		} finally {
			if (!exceptionCatched) {
				// Clear Database
				eliminarOferta(nuevaOferta.getOfertaId());
			}
		}

	}
	
	@Test(expected = InstanceNotFoundException.class)
	public void testBuscarOfertaNoExistente() throws InstanceNotFoundException {

		ofertaService.buscarOferta(ID_OFERTA_NO_EXISTENTE);

	}
	
	@Test
	public void testActualizarOferta() throws InputValidationException, InstanceNotFoundException, FechaInvalidaException, OfertaReservedException, PrecioDescontadoException {

		Oferta oferta = crearOferta(getOfertaValida());
		try {

			oferta.setNombre("Oferta de test");
			oferta.setDescripcion("Descripción de la oferta de test");
			oferta.setPrecioReal(20);
			oferta.setPrecioDescontado(15);
			
			Calendar nuevaFechaLimite = oferta.getFechaReservar();
			nuevaFechaLimite.add(Calendar.DAY_OF_YEAR, -3);
			oferta.setFechaReservar(nuevaFechaLimite);
			oferta.setEstado(EstadoOferta.INVALIDA);

			ofertaService.actualizarOferta(oferta);

			Oferta ofertaActualizada = ofertaService.buscarOferta(oferta.getOfertaId());
			assertEquals(oferta, ofertaActualizada);

		} finally {
			// Clear Database
			eliminarOferta(oferta.getOfertaId());
		}

	}
	
	@Test(expected = InputValidationException.class)
	public void testActualizarOfertaInvalida() throws InputValidationException, InstanceNotFoundException, FechaInvalidaException, OfertaReservedException, PrecioDescontadoException {

		Oferta oferta = crearOferta(getOfertaValida());
		try {
			// Check movie title not null
			oferta = ofertaService.buscarOferta(oferta.getOfertaId());
			oferta.setNombre(null);
			ofertaService.actualizarOferta(oferta);
		} finally {
			// Clear Database
			eliminarOferta(oferta.getOfertaId());
		}

	}
	
	@Test(expected = InstanceNotFoundException.class)
	public void testActualizarOfertaNoExistente() throws InputValidationException, InstanceNotFoundException, FechaInvalidaException, PrecioDescontadoException, OfertaReservedException {

		Oferta oferta = getOfertaValida();
		oferta.setOfertaId(ID_OFERTA_NO_EXISTENTE);
		ofertaService.actualizarOferta(oferta);
		eliminarOferta(oferta.getOfertaId());

	}
	
	@Test(expected = InstanceNotFoundException.class)
	public void testEliminarOferta() throws InstanceNotFoundException, OfertaReservedException {

		Oferta oferta = crearOferta(getOfertaValida());
		boolean exceptionCatched = false;
		try {
			ofertaService.eliminarOferta(oferta.getOfertaId());
		} catch (InstanceNotFoundException e) {
			exceptionCatched = true;
		}
		assertTrue(!exceptionCatched);

		ofertaService.buscarOferta(oferta.getOfertaId());

	}
	
	@Test(expected = InstanceNotFoundException.class)
	public void testEliminarOfertaNoExistente() throws InstanceNotFoundException, OfertaReservedException {

		ofertaService.eliminarOferta(ID_OFERTA_NO_EXISTENTE);

	}
	
	public void limpiarBaseDeDatos() throws InstanceNotFoundException, OfertaReservedException {
		List<Oferta> ofertasEncontradas = ofertaService.buscarOfertas("", null, null);
		int i = 0;
		int j = 0;
		for (i = 0; i < ofertasEncontradas.size(); i++){
			List<Reserva> reservasEncontradas = ofertaService.buscarReservasDeUnaOferta(ofertasEncontradas.get(i).getOfertaId());
			for (j = 0; j < reservasEncontradas.size(); j++) {
				eliminarReserva(reservasEncontradas.get(j).getReservaId());
			}
			eliminarOferta(ofertasEncontradas.get(i).getOfertaId());
		}
	}
	
	@Test
	public void testBuscarOfertas() throws OfertaReservedException, InstanceNotFoundException, InputValidationException, FechaInvalidaException, PrecioDescontadoException, OfertaInvalidaException {

		// Añadir ofertas
		List<Oferta> ofertas = new LinkedList<Oferta>();
		
		Oferta oferta1 = crearOferta(getOfertaValida("Nombre oferta 1"));
		ofertas.add(oferta1);
		
		Oferta oferta2 = crearOferta(getOfertaValida("Nombre oferta 2"));
		
		Calendar nuevaFechaReservar = oferta2.getFechaReservar();
		nuevaFechaReservar.add(Calendar.YEAR, 1);
		nuevaFechaReservar.add(Calendar.MONTH, 1);
		oferta2.setFechaReservar(nuevaFechaReservar);
		
		ofertaService.actualizarOferta(oferta2);
		oferta2 = ofertaService.buscarOferta(oferta2.getOfertaId());
		
		ofertas.add(oferta2);
		
		Oferta oferta3 = crearOferta(getOfertaValida("Nombre oferta 3"));
		ofertaService.invalidarOferta(oferta3.getOfertaId());
		oferta3 = ofertaService.buscarOferta(oferta3.getOfertaId());
		oferta3.setFechaReservar(nuevaFechaReservar);
		ofertaService.actualizarOferta(oferta3);
		ofertas.add(oferta3);

		try {

			List<Oferta> ofertasEncontradas = ofertaService.buscarOfertas("2", null, null);
			assertEquals(1, ofertasEncontradas.size());

			ofertasEncontradas = ofertaService.buscarOfertas("otracosa", null, null);
			assertEquals(0, ofertasEncontradas.size());
			
	//		ofertasEncontradas = ofertaService.buscarOfertas("oferta", EstadoOferta.INVALIDA, null);
	//		assertEquals(1, ofertasEncontradas.size());
	//		assertEquals(oferta3.getOfertaId(), ofertasEncontradas.get(0).getOfertaId());
			
			ofertasEncontradas = ofertaService.buscarOfertas("oferta", true, null);
			assertEquals(2, ofertasEncontradas.size());
			
			Calendar fecha = Calendar.getInstance();
			fecha.add(Calendar.YEAR, 5);
			ofertasEncontradas = ofertaService.buscarOfertas("oferta", null, fecha);
			assertEquals(0, ofertasEncontradas.size());
			
			Calendar fecha2 = oferta2.getFechaReclamar();
			fecha2.add(Calendar.MONTH, -1);
			ofertasEncontradas = ofertaService.buscarOfertas("oferta", true, fecha2);
			assertEquals(1, ofertasEncontradas.size());
			assertEquals(oferta2.getOfertaId(), ofertasEncontradas.get(0).getOfertaId());
			
	//		ofertasEncontradas = ofertaService.buscarOfertas("oferta", EstadoOferta.INVALIDA, fecha2);
	//		assertEquals(1, ofertasEncontradas.size());
	//		assertEquals(oferta3.getOfertaId(), ofertasEncontradas.get(0).getOfertaId());
			
			ofertasEncontradas = ofertaService.buscarOfertas("oferta", null, fecha2);
			assertEquals(2, ofertasEncontradas.size());
			
			
		} finally {
			// Clear Database
			for (Oferta oferta : ofertas) {
				eliminarOferta(oferta.getOfertaId());
			}
			
		}

	}
	
	
	//Este test ya no tiene sentido porque el servicio ya no contempla la búsqueda de ofertas inválidas
	/*
	@Test
	public void testBuscarOfertasInvalidas() throws OfertaReservedException, InputValidationException, InstanceNotFoundException, FechaInvalidaException, PrecioDescontadoException {
		
		Oferta oferta1 = crearOferta(getOfertaValida("Nombre oferta 1"));
		Oferta oferta2 = crearOferta(getOfertaValida("Nombre oferta 2"));
		Oferta oferta3 = crearOferta(getOfertaValida("Nombre oferta 3"));
						
		oferta3.setEstado(EstadoOferta.INVALIDA);
		ofertaService.actualizarOferta(oferta3);
		
		List<Oferta> ofertas = new LinkedList<Oferta>();
		ofertas.add(oferta3);

		try {
			List<Oferta> ofertasEncontradas = ofertaService.buscarOfertas("oferta", EstadoOferta.INVALIDA, null);
			assertEquals(ofertas, ofertasEncontradas);

		} finally {
			
			limpiarBaseDeDatos();
		}

	} */
	
	@Test
	public void testOfertasPorFecha() throws OfertaReservedException, InputValidationException, InstanceNotFoundException, FechaInvalidaException, PrecioDescontadoException {
		
		Oferta oferta1 = crearOferta(getOfertaValida("Nombre oferta 1"));
		
		Calendar fechaReservar = oferta1.getFechaReservar();
		fechaReservar.add(Calendar.YEAR, 1);
		oferta1.setFechaReservar(fechaReservar);
		ofertaService.actualizarOferta(oferta1);
		
		Oferta oferta2 = crearOferta(getOfertaValida("Nombre oferta 2"));
		Oferta oferta3 = crearOferta(getOfertaValida("Nombre oferta 3"));
		
		List<Oferta> ofertas = new LinkedList<Oferta>();
		ofertas.add(oferta1);

		try {
			Calendar fecha = Calendar.getInstance();
			fecha.add(Calendar.MONTH, 1);
			
			List<Oferta> ofertasEncontradas = ofertaService.buscarOfertas("oferta", true, fecha);
						
			assertEquals(ofertas, ofertasEncontradas);
			
		} finally {
			limpiarBaseDeDatos();
		}

	}
	
	@Test
	public void testReservarYBuscarReserva() throws InstanceNotFoundException, InputValidationException, ReservaExpirationException, OfertaReservedException, OfertaExpirationException, OfertaInvalidaException, OfertaReservedByUserException {

		Oferta oferta = crearOferta(getOfertaValida());
		Reserva reserva = null;
		
		try {
			
			/* Reservar una oferta. */
			Calendar fechaReservaNoCaducada = Calendar.getInstance();
			fechaReservaNoCaducada.add(Calendar.DAY_OF_MONTH, LIMITE_DIAS_RESERVA);
			fechaReservaNoCaducada.set(Calendar.MILLISECOND, 0);

			reserva = ofertaService.reservarOferta(oferta.getOfertaId(), EMAIL_USUARIO_1, TARJETA_CREDITO_VALIDA);

			Calendar fechaReservaCaducada = Calendar.getInstance();
			fechaReservaCaducada.add(Calendar.DAY_OF_MONTH, LIMITE_DIAS_RESERVA);
			fechaReservaCaducada.set(Calendar.MILLISECOND, 0);

			/* Buscar reserva. */
			Reserva reservaEncontrada = ofertaService.buscarReserva(reserva.getReservaId());
			
			/* Comprobar reserva. */
			assertEquals(reserva, reservaEncontrada);
			assertEquals(TARJETA_CREDITO_VALIDA, reservaEncontrada.getTarjeta());
			assertEquals(EMAIL_USUARIO_1, reservaEncontrada.getEmail());
			assertEquals(oferta.getOfertaId(), reservaEncontrada.getOfertaId());

		} finally {
			/* Clear database: remove sale (if created) and movie. */
			if (reserva != null) {
				eliminarReserva(reserva.getReservaId());
			}
			eliminarOferta(oferta.getOfertaId());
		}

	}
	
	@Test(expected = InputValidationException.class)
	public void testReservarTarjetaInvalida() throws InputValidationException, InstanceNotFoundException, OfertaReservedException, OfertaExpirationException, OfertaInvalidaException, OfertaReservedByUserException {

		Oferta oferta = crearOferta(getOfertaValida());
		try {
			Reserva reserva = ofertaService.reservarOferta(oferta.getOfertaId(), EMAIL_USUARIO_1, TARJETA_CREDITO_INVALIDA);
			eliminarReserva(reserva.getReservaId());
		} finally {
			/* Clear database. */
			eliminarOferta(oferta.getOfertaId());
		}

	}
	
	@Test(expected = InstanceNotFoundException.class)
	public void testReservaNoExistente() throws InputValidationException, InstanceNotFoundException, OfertaExpirationException, OfertaInvalidaException, OfertaReservedByUserException {

		Reserva reserva = ofertaService.reservarOferta(ID_OFERTA_NO_EXISTENTE, EMAIL_USUARIO_1,
				TARJETA_CREDITO_VALIDA);
		/* Clear database. */
		eliminarReserva(reserva.getReservaId());

	}

	@Test(expected = InstanceNotFoundException.class)
	public void testBuscarReservaNoExistente() throws InstanceNotFoundException,
			ReservaExpirationException {

		ofertaService.buscarReserva(ID_RESERVA_NO_EXISTENTE);

	}

	//No tenemos forma de comprobar esta excepción porque no podemos introducir una fecha pasada en oferta.setFechaReclamar
	//Tampoco es una funcionalidad pedida en el enunciado, en la hora de reservar, solamente nos piden que comprobemos que siguen
	//siendo válidas
	
	
//	@Test(expected = ReservaExpirationException.class)
//	public void testReservaCaducada() throws InputValidationException,
//			ReservaExpirationException, InstanceNotFoundException, OfertaReservedException, OfertaExpirationException, OfertaInvalidaException, ReservaNoValidaException, FechaInvalidaException, PrecioDescontadoException {
//
//		Oferta oferta = crearOferta(getOfertaValida());
//		Reserva reserva = null;
//		try {
//			reserva = ofertaService.reservarOferta(oferta.getOfertaId(), EMAIL_USUARIO_1, TARJETA_CREDITO_VALIDA);	
//			Calendar fechaReclamar = oferta.getFechaReclamar();
//			fechaReclamar.add(Calendar.DAY_OF_YEAR, -25);
//			oferta.setFechaReclamar(fechaReclamar);
//			ofertaService.actualizarOferta(oferta);
//			ofertaService.reclamarReserva(reserva.getReservaId());
//			
//		} finally {
//			if (reserva != null) {
//				eliminarReserva(reserva.getReservaId());
//			}
//			eliminarOferta(oferta.getOfertaId());
//		}
//	}
	
	@Test
	public void testBuscarReservasDeUnaOferta() throws InputValidationException, InstanceNotFoundException, OfertaReservedException, OfertaExpirationException, OfertaInvalidaException, OfertaReservedByUserException {
		
		Oferta oferta = crearOferta(getOfertaValida());
		List<Reserva> reservas = new LinkedList<Reserva>();
		
		try {
			Reserva reserva1 = ofertaService.reservarOferta(oferta.getOfertaId(), EMAIL_USUARIO_1, TARJETA_CREDITO_VALIDA);
			Reserva reserva2 = ofertaService.reservarOferta(oferta.getOfertaId(), EMAIL_USUARIO_2, TARJETA_CREDITO_VALIDA);
			Reserva reserva3 = ofertaService.reservarOferta(oferta.getOfertaId(), EMAIL_USUARIO_3, TARJETA_CREDITO_VALIDA);
			
			reservas.add(reserva1);
			reservas.add(reserva2);
			reservas.add(reserva3);
		
			List<Reserva> reservasEncontradas = ofertaService.buscarReservasDeUnaOferta(oferta.getOfertaId());
			assertEquals(reservasEncontradas, reservas);
			
		} finally {
			for (Reserva reserva : reservas) {
				eliminarReserva(reserva.getReservaId());
			}
			
			eliminarOferta(oferta.getOfertaId());
		}
		
	}
	
	@Test
	public void testInvalidarOferta() throws InstanceNotFoundException, InputValidationException, FechaInvalidaException, OfertaReservedException, PrecioDescontadoException, OfertaExpirationException, OfertaInvalidaException, OfertaReservedByUserException {
		
		Oferta oferta = crearOferta(getOfertaValida());	
		List<Reserva> reservas = new LinkedList<Reserva>();
		
		try {
			Reserva reserva1 = ofertaService.reservarOferta(oferta.getOfertaId(), EMAIL_USUARIO_1, TARJETA_CREDITO_VALIDA);
			Reserva reserva2 = ofertaService.reservarOferta(oferta.getOfertaId(), EMAIL_USUARIO_2, TARJETA_CREDITO_VALIDA);
			Reserva reserva3 = ofertaService.reservarOferta(oferta.getOfertaId(), EMAIL_USUARIO_3, TARJETA_CREDITO_VALIDA);
			
			reservas.add(reserva1);
			reservas.add(reserva2);
			reservas.add(reserva3);
		
			ofertaService.invalidarOferta(oferta.getOfertaId());
			oferta = ofertaService.buscarOferta(oferta.getOfertaId());
			assertEquals(oferta.getEstado(), EstadoOferta.INVALIDA);
			List<Reserva> reservasEncontradas = ofertaService.buscarReservasDeUnaOferta(oferta.getOfertaId());
			
			int i = 0;
			for (i = 0; i < reservasEncontradas.size(); i++) {
				assertEquals(reservasEncontradas.get(i).getEstado(), EstadoReserva.INVALIDA);
			}
			
			
		} finally {
			for (Reserva reserva : reservas) {
				eliminarReserva(reserva.getReservaId());
			}
			
			eliminarOferta(oferta.getOfertaId());
		}
		
		
		
	}
	
	@Test(expected = FechaInvalidaException.class)
	public void testAdelantarFechaLimite() throws InputValidationException, InstanceNotFoundException, FechaInvalidaException, PrecioDescontadoException, OfertaReservedException, OfertaExpirationException, OfertaInvalidaException, OfertaReservedByUserException {
		
		Oferta oferta = crearOferta(getOfertaValida());	
		List<Reserva> reservas = new LinkedList<Reserva>();
		
		try {
			Reserva reserva1 = ofertaService.reservarOferta(oferta.getOfertaId(), EMAIL_USUARIO_1, TARJETA_CREDITO_VALIDA);
			Reserva reserva2 = ofertaService.reservarOferta(oferta.getOfertaId(), EMAIL_USUARIO_2, TARJETA_CREDITO_VALIDA);
			Reserva reserva3 = ofertaService.reservarOferta(oferta.getOfertaId(), EMAIL_USUARIO_3, TARJETA_CREDITO_VALIDA);
			
			reservas.add(reserva1);
			reservas.add(reserva2);
			reservas.add(reserva3);
		
			Calendar nuevaFechaLimite = oferta.getFechaReservar();
			nuevaFechaLimite.add(Calendar.DAY_OF_YEAR, -3);
			oferta.setFechaReservar(nuevaFechaLimite);
			ofertaService.actualizarOferta(oferta);
			
			
		} finally {
			for (Reserva reserva : reservas) {
				eliminarReserva(reserva.getReservaId());
			}
	
			eliminarOferta(oferta.getOfertaId());
		}		
	}
	
	@Test(expected = OfertaReservedException.class)
	public void testEliminarOfertaConReservas() throws InstanceNotFoundException, InputValidationException, OfertaReservedException, OfertaExpirationException, OfertaInvalidaException, OfertaReservedByUserException {
		
		Oferta oferta = crearOferta(getOfertaValida());	
		List<Reserva> reservas = new LinkedList<Reserva>();
		
		try {
			Reserva reserva1 = ofertaService.reservarOferta(oferta.getOfertaId(), EMAIL_USUARIO_1, TARJETA_CREDITO_VALIDA);
			Reserva reserva2 = ofertaService.reservarOferta(oferta.getOfertaId(), EMAIL_USUARIO_2, TARJETA_CREDITO_VALIDA);
			Reserva reserva3 = ofertaService.reservarOferta(oferta.getOfertaId(), EMAIL_USUARIO_3, TARJETA_CREDITO_VALIDA);
			
			reservas.add(reserva1);
			reservas.add(reserva2);
			reservas.add(reserva3);
		
			ofertaService.eliminarOferta(oferta.getOfertaId());
			
			
		} finally {
			for (Reserva reserva : reservas) {
				eliminarReserva(reserva.getReservaId());
			}
			
			eliminarOferta(oferta.getOfertaId());
		}
	}
	
	@Test(expected = PrecioDescontadoException.class)
	public void testAumentarPrecioDescontado() throws InputValidationException, InstanceNotFoundException, FechaInvalidaException, PrecioDescontadoException, OfertaReservedException, OfertaExpirationException, OfertaInvalidaException, OfertaReservedByUserException {
		
		
		Oferta oferta = crearOferta(getOfertaValida());	
		List<Reserva> reservas = new LinkedList<Reserva>();
		
		try {
			Reserva reserva1 = ofertaService.reservarOferta(oferta.getOfertaId(), EMAIL_USUARIO_1, TARJETA_CREDITO_VALIDA);
			Reserva reserva2 = ofertaService.reservarOferta(oferta.getOfertaId(), EMAIL_USUARIO_2, TARJETA_CREDITO_VALIDA);
			Reserva reserva3 = ofertaService.reservarOferta(oferta.getOfertaId(), EMAIL_USUARIO_3, TARJETA_CREDITO_VALIDA);
			
			reservas.add(reserva1);
			reservas.add(reserva2);
			reservas.add(reserva3);
		
			oferta.setPrecioDescontado(oferta.getPrecioDescontado() + 5f);
			ofertaService.actualizarOferta(oferta);
			
			
		} finally {
			for (Reserva reserva : reservas) {
				eliminarReserva(reserva.getReservaId());
			}
	
			eliminarOferta(oferta.getOfertaId());
		}
	}
	
	public void testAdelantarFechaReclamar() {
		
	}
	
	@Test
	public void testBuscarReservasPorUsuario() throws InstanceNotFoundException, InputValidationException, OfertaReservedException, OfertaExpirationException, OfertaInvalidaException, OfertaReservedByUserException {
		
		Oferta oferta = crearOferta(getOfertaValida());	
		Oferta oferta2 = crearOferta(getOfertaValida());	
		Oferta oferta3 = crearOferta(getOfertaValida());	
		Oferta oferta4 = crearOferta(getOfertaValida());	
		Oferta oferta5 = crearOferta(getOfertaValida());	
		List<Reserva> reservas1 = new LinkedList<Reserva>();
		List<Reserva> reservas2 = new LinkedList<Reserva>();
		
		try {
			Reserva reserva1 = ofertaService.reservarOferta(oferta.getOfertaId(), EMAIL_USUARIO_1, TARJETA_CREDITO_VALIDA);
			Reserva reserva2 = ofertaService.reservarOferta(oferta2.getOfertaId(), EMAIL_USUARIO_1, TARJETA_CREDITO_VALIDA);
			Reserva reserva3 = ofertaService.reservarOferta(oferta3.getOfertaId(), EMAIL_USUARIO_1, TARJETA_CREDITO_VALIDA);
			
			reservas1.add(reserva1);
			reservas1.add(reserva2);
			reservas1.add(reserva3);
			
			Reserva reserva4 = ofertaService.reservarOferta(oferta4.getOfertaId(), EMAIL_USUARIO_1, TARJETA_CREDITO_VALIDA);
			Reserva reserva5 = ofertaService.reservarOferta(oferta5.getOfertaId(), EMAIL_USUARIO_1, TARJETA_CREDITO_VALIDA);		
			
			reservas1.add(reserva4);
			reservas1.add(reserva5);
		
			List<Reserva> reservasEncontradas = ofertaService.buscarReservasDeUnUsuario(EMAIL_USUARIO_1, false);
			assertEquals(reservasEncontradas, reservas1);
			
			
		} finally {
			for (Reserva reserva : reservas1) {
				eliminarReserva(reserva.getReservaId());
			}
			
			for (Reserva reserva : reservas2) {
				eliminarReserva(reserva.getReservaId());
			}
	
			eliminarOferta(oferta.getOfertaId());
		}
		
	}
	
	@Test
	public void testBuscarReservasValidasDeUnUsuario() throws InstanceNotFoundException, InputValidationException, OfertaReservedException, ReservaExpirationException, OfertaExpirationException, OfertaInvalidaException, OfertaReservedByUserException {
		
		Oferta oferta = crearOferta(getOfertaValida());	
		Oferta oferta2 = crearOferta(getOfertaValida());	
		Oferta oferta3 = crearOferta(getOfertaValida());	
		Oferta oferta4 = crearOferta(getOfertaValida());	
		Oferta oferta5 = crearOferta(getOfertaValida());
		
		List<Reserva> reservas1 = new LinkedList<Reserva>();
		List<Reserva> reservas2 = new LinkedList<Reserva>();
		List<Reserva> reservasValidas = new LinkedList<Reserva>();
		
		try {
			Reserva reserva1 = ofertaService.reservarOferta(oferta.getOfertaId(), EMAIL_USUARIO_1, TARJETA_CREDITO_VALIDA);
			Reserva reserva2 = ofertaService.reservarOferta(oferta2.getOfertaId(), EMAIL_USUARIO_1, TARJETA_CREDITO_VALIDA);
			Reserva reserva3 = ofertaService.reservarOferta(oferta3.getOfertaId(), EMAIL_USUARIO_1, TARJETA_CREDITO_VALIDA);
			
			reserva2.setEstado(EstadoReserva.INVALIDA);
			actualizarReserva(reserva2);
			
			reserva3.setEstado(EstadoReserva.INVALIDA);
			actualizarReserva(reserva3);
			
			reservasValidas.add(reserva1);
			
			reservas1.add(reserva1);
			reservas1.add(reserva2);
			reservas1.add(reserva3);
			
			Reserva reserva4 = ofertaService.reservarOferta(oferta4.getOfertaId(), EMAIL_USUARIO_2, TARJETA_CREDITO_VALIDA);
			Reserva reserva5 = ofertaService.reservarOferta(oferta5.getOfertaId(), EMAIL_USUARIO_2, TARJETA_CREDITO_VALIDA);		
			
			reservas2.add(reserva4);
			reservas2.add(reserva5);
		
			List<Reserva> reservasEncontradas = ofertaService.buscarReservasDeUnUsuario(EMAIL_USUARIO_1, true);
			assertEquals(reservasEncontradas, reservasValidas);
			
			
		} finally {
			
			for (Reserva reserva : reservas1) {
				eliminarReserva(reserva.getReservaId());
			}
			
			for (Reserva reserva : reservas2) {
				eliminarReserva(reserva.getReservaId());
			}
	
			eliminarOferta(oferta.getOfertaId());
			eliminarOferta(oferta2.getOfertaId());
			eliminarOferta(oferta3.getOfertaId());
			eliminarOferta(oferta4.getOfertaId());
			eliminarOferta(oferta5.getOfertaId());
		}
		
	}
	
	@Test
	public void testReclamarReserva() throws OfertaReservedException, InstanceNotFoundException, InputValidationException, ReservaNoValidaException, ReservaExpirationException, OfertaExpirationException, OfertaInvalidaException, OfertaReservedByUserException {
		
		Oferta oferta = crearOferta(getOfertaValida());	
		List<Reserva> reservas = new LinkedList<Reserva>();
		
		try {
			Reserva reserva = ofertaService.reservarOferta(oferta.getOfertaId(), EMAIL_USUARIO_1, TARJETA_CREDITO_VALIDA);
			reservas.add(reserva);
			
			ofertaService.reclamarReserva(reserva.getCodigo());	
			reserva = ofertaService.buscarReserva(reserva.getReservaId());
			
			assertEquals(reserva.getEstado(), EstadoReserva.RECLAMADA);
			
			
		} finally {
			
			for (Reserva reserva : reservas) {
				eliminarReserva(reserva.getReservaId());
			}
			
			eliminarOferta(oferta.getOfertaId());
		}
		
	}
	
	@Test(expected = ReservaNoValidaException.class)
	public void testReclamarReservaYaReclamada() throws OfertaReservedException, InstanceNotFoundException, InputValidationException, ReservaNoValidaException, ReservaExpirationException, OfertaExpirationException, OfertaInvalidaException, OfertaReservedByUserException {
		
		Oferta oferta = crearOferta(getOfertaValida());	
		List<Reserva> reservas = new LinkedList<Reserva>();
		
		try {
			Reserva reserva = ofertaService.reservarOferta(oferta.getOfertaId(), EMAIL_USUARIO_1, TARJETA_CREDITO_VALIDA);
			reservas.add(reserva);
			
			ofertaService.reclamarReserva(reserva.getCodigo());	
			ofertaService.reclamarReserva(reserva.getCodigo());
									
			
		} finally {
			
			for (Reserva reserva : reservas) {
				eliminarReserva(reserva.getReservaId());
			}
			
			eliminarOferta(oferta.getOfertaId());
		}
		
	}
	
	@Test(expected = ReservaNoValidaException.class)
	public void testReclamarReservaAnulada() throws OfertaReservedException, InstanceNotFoundException, InputValidationException, ReservaNoValidaException, ReservaExpirationException, FechaInvalidaException, PrecioDescontadoException, OfertaExpirationException, OfertaInvalidaException, OfertaReservedByUserException {
		
		Oferta oferta = crearOferta(getOfertaValida());	
		List<Reserva> reservas = new LinkedList<Reserva>();
		
		try {
			Reserva reserva = ofertaService.reservarOferta(oferta.getOfertaId(), EMAIL_USUARIO_1, TARJETA_CREDITO_VALIDA);
			reservas.add(reserva);
			
			ofertaService.invalidarOferta(oferta.getOfertaId());			
			ofertaService.reclamarReserva(reserva.getCodigo());	
									
			
		} finally {
			
			limpiarBaseDeDatos();
		}
		
	}

	
}
