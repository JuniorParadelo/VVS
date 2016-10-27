package es.udc.pojoapp.test.model.bet;

import static es.udc.pojoapp.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.udc.pojoapp.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.pojoapp.model.betType.BetType;
import es.udc.pojoapp.model.bettypeservice.BetTypeService;
import es.udc.pojoapp.model.bettypeservice.BetTypesIgualesException;
import es.udc.pojoapp.model.bettypeservice.EventEmpezadoException;
import es.udc.pojoapp.model.bettypeservice.EventoNoEmpezadoException;
import es.udc.pojoapp.model.bettypeservice.OpcionesActualizadasException;
import es.udc.pojoapp.model.bettypeservice.UnicaGanadoraException;
import es.udc.pojoapp.model.category.Category;
import es.udc.pojoapp.model.event.Event;
import es.udc.pojoapp.model.eventservice.DuplicateEventException;
import es.udc.pojoapp.model.eventservice.EventService;
import es.udc.pojoapp.model.eventservice.PastEventException;
import es.udc.pojoapp.model.option.Option;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class BetTypeServiceTest {

	@Autowired
	private EventService eventService;

	@Autowired
	private BetTypeService betService;

	private Category createCat(String name) {
		return eventService.createCategory(name);
	}

	private Event defaultEvent(Category cat) throws PastEventException, DuplicateEventException {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_MONTH, 11);
		return eventService.createEvent("Deportivo - Real Madrid", date, cat.getIdentificador());
	}

	private BetType createBetType(String question, Event event) {
		return new BetType(question, event, false);
	}

	private Option createOption(String name, BetType betType) {
		return new Option(name, 1.5, betType);
	}

	// PR_UN-029
	@Test(expected = BetTypesIgualesException.class)
	@Transactional
	public void createOpcionesDeApuestaTest1() throws InstanceNotFoundException, EventEmpezadoException,
			PastEventException, DuplicateEventException, BetTypesIgualesException {
		Event evento = defaultEvent(createCat("Futbol"));
		BetType bet = createBetType("Ganador?", evento);
		Option opcion1 = createOption("Deportivo", bet);
		Option opcion2 = createOption("Real Madrid", bet);
		List<Option> options = new ArrayList<Option>();
		options.add(opcion1);
		options.add(opcion2);
		betService.create(bet, options, evento.getId());

		betService.create(bet, options, evento.getId());
	}

	// PR_UN-030
	@Test(expected = EventEmpezadoException.class)
	@Transactional
	public void createOpcionesDeApuestaTest() throws InstanceNotFoundException, EventEmpezadoException,
			PastEventException, DuplicateEventException, BetTypesIgualesException {
		Event evento = defaultEvent(createCat("Futbol"));
		evento.getDate().set(Calendar.YEAR, 2014);
		BetType bet = createBetType("Ganador?", evento);
		Option opcion1 = createOption("Deportivo", bet);
		Option opcion2 = createOption("Real Madrid", bet);
		List<Option> options = new ArrayList<Option>();
		options.add(opcion1);
		options.add(opcion2);
		betService.create(bet, options, evento.getId());
	}

	// PR_UN-027
	// PR_UN-028
	@Test
	@Transactional
	public void createOptionesDeApuestaTest() throws InstanceNotFoundException, EventEmpezadoException,
			PastEventException, DuplicateEventException, BetTypesIgualesException {

		Event evento = defaultEvent(createCat("Futbol"));
		BetType bet = createBetType("Ganador?", evento);
		Option opcion1 = createOption("Deportivo", bet);
		Option opcion2 = createOption("Real Madrid", bet);
		List<Option> options = new ArrayList<Option>();
		options.add(opcion1);
		options.add(opcion2);
		betService.create(bet, options, evento.getId());

		// COMPROBACION DE DATOS
		Event eventoTest = eventService.findEvent(evento.getId());
		BetType betTest = betService.findBetType(bet.getId());
		assertTrue(eventoTest.getBetTypes().contains(betTest));
		assertTrue(betTest.getOptions().containsAll(options));

		assertEquals(betTest.getOptions().size(), 2);

	}

	// PR_UN-032
	@Test(expected = UnicaGanadoraException.class)
	@Transactional
	public void actualizarTest3() throws InstanceNotFoundException, EventEmpezadoException, EventoNoEmpezadoException,
			OpcionesActualizadasException, PastEventException, DuplicateEventException, BetTypesIgualesException,
			UnicaGanadoraException {
		Event evento = defaultEvent(createCat("Baloncesto"));
		BetType bet = createBetType("Ganador?", evento);
		bet.setUnica(true);
		Option opcion1 = createOption("Deportivo", bet);
		Option opcion2 = createOption("Real Madrid", bet);
		List<Option> options = new ArrayList<Option>();
		options.add(opcion1);
		options.add(opcion2);
		betService.create(bet, options, evento.getId());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -2);
		evento.setDate(cal);
		List<Long> optionIds = new ArrayList<Long>();
		optionIds.add(opcion1.getId());
		optionIds.add(opcion2.getId());
		betService.updateState(bet.getId(), optionIds);
	}

	// PR_UN-031
	@Test
	@Transactional
	public void actualizarTest1() throws InstanceNotFoundException, EventEmpezadoException, EventoNoEmpezadoException,
			OpcionesActualizadasException, PastEventException, DuplicateEventException, BetTypesIgualesException,
			UnicaGanadoraException {
		Event evento = defaultEvent(createCat("Baloncesto"));

		BetType bet = createBetType("Ganador?", evento);
		Option opcion1 = createOption("Deportivo", bet);
		Option opcion2 = createOption("Real Madrid", bet);
		List<Option> options = new ArrayList<Option>();
		options.add(opcion1);
		options.add(opcion2);
		betService.create(bet, options, evento.getId());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -2);
		evento.setDate(cal);
		List<Long> optionIds = new ArrayList<Long>();
		optionIds.add(opcion1.getId());
		betService.updateState(bet.getId(), optionIds);
	}

	// PR_UN-033
	@Test(expected = OpcionesActualizadasException.class)
	@Transactional
	public void actualizarTest2() throws InstanceNotFoundException, EventoNoEmpezadoException,
			OpcionesActualizadasException, EventEmpezadoException, PastEventException, DuplicateEventException,
			BetTypesIgualesException, UnicaGanadoraException {
		Event evento = defaultEvent(createCat("Baloncesto"));
		BetType bet = createBetType("Ganador?", evento);
		Option opcion1 = createOption("Deportivo", bet);
		Option opcion2 = createOption("Real Madrid", bet);
		List<Option> options = new ArrayList<Option>();
		options.add(opcion1);
		options.add(opcion2);
		betService.create(bet, options, evento.getId());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -2);
		evento.setDate(cal);
		List<Long> optionIds = new ArrayList<Long>();
		optionIds.add(opcion1.getId());
		System.out.println(opcion1.getVersion());
		System.out.println(opcion1.getWin());
		System.out.println(opcion1.getId());
		betService.updateState(bet.getId(), optionIds);
		System.out.println(opcion1.getVersion());
		System.out.println(opcion1.getWin());
		System.out.println(opcion1.getId());
		betService.updateState(bet.getId(), optionIds);
		System.out.println(opcion1.getVersion());
	}

	// PR_UN-031
	@Test
	@Transactional
	public void actualizarTest() throws InstanceNotFoundException, EventEmpezadoException, EventoNoEmpezadoException,
			OpcionesActualizadasException, PastEventException, DuplicateEventException, BetTypesIgualesException,
			UnicaGanadoraException {

		Event evento = defaultEvent(createCat("Baloncesto"));
		BetType bet = createBetType("Ganador?", evento);
		Option opcion1 = createOption("Deportivo", bet);
		Option opcion2 = createOption("Real Madrid", bet);
		List<Option> options = new ArrayList<Option>();
		options.add(opcion1);
		options.add(opcion2);
		betService.create(bet, options, evento.getId());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -2);
		evento.setDate(cal);
		List<Long> optionIds = new ArrayList<Long>();
		optionIds.add(opcion1.getId());
		betService.updateState(bet.getId(), optionIds);

		// COMPROBACIONES

		BetType betTest = betService.findBetType(bet.getId());

		List<Option> optionTest = betTest.getOptions();

		if (optionTest.get(0).getId() == optionIds.get(0)) {
			assertTrue(optionTest.get(0).getWin());
		} else {
			assertFalse(optionTest.get(0).getWin());
		}
		if (optionTest.get(1).getId() == optionIds.get(0)) {
			assertTrue(optionTest.get(1).getWin());
		} else {
			assertFalse(optionTest.get(1).getWin());
		}
	}
}
