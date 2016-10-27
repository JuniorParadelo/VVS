package es.udc.pojoapp.test.model.bet;

import static es.udc.pojoapp.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.udc.pojoapp.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.assertEquals;
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

import es.udc.pojo.modelutil.exceptions.DuplicateInstanceException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.pojoapp.model.bet.Bet;
import es.udc.pojoapp.model.betType.BetType;
import es.udc.pojoapp.model.betservice.BetBlock;
import es.udc.pojoapp.model.betservice.BetService;
import es.udc.pojoapp.model.bettypeservice.BetTypeService;
import es.udc.pojoapp.model.bettypeservice.BetTypesIgualesException;
import es.udc.pojoapp.model.bettypeservice.EventEmpezadoException;
import es.udc.pojoapp.model.category.Category;
import es.udc.pojoapp.model.event.Event;
import es.udc.pojoapp.model.eventservice.DuplicateEventException;
import es.udc.pojoapp.model.eventservice.EventService;
import es.udc.pojoapp.model.eventservice.PastEventException;
import es.udc.pojoapp.model.option.Option;
import es.udc.pojoapp.model.userprofile.UserProfile;
import es.udc.pojoapp.model.userservice.UserProfileDetails;
import es.udc.pojoapp.model.userservice.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class BetServiceTest {

	@Autowired
	private BetService betService;

	@Autowired
	private BetTypeService optionService;

	@Autowired
	private EventService eventService;

	@Autowired
	private UserService userService;

	private Category createCat() {
		return eventService.createCategory("Futbol");
	}

	private Event createEvent(Category cat) throws PastEventException, DuplicateEventException {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_MONTH, 2);
		return eventService.createEvent("Deportivo - Real Madrid", date, cat.getIdentificador());
	}

	private UserProfile createUser() throws DuplicateInstanceException {
		return userService.registerUser("user", "password", new UserProfileDetails("name", "lname", "name@email.es"));
	}

	private List<Option> createoptions() {
		Option option1 = new Option("Ronaldo", 1.05, null);
		Option option2 = new Option("Benzema", 1.15, null);
		Option option3 = new Option("Arbeloa", 11.05, null);
		List<Option> options = new ArrayList<Option>();
		options.add(option1);
		options.add(option2);
		options.add(option3);
		return options;
	}

	private BetType createBetType(Event e) throws EventEmpezadoException, BetTypesIgualesException {
		try {
			BetType bet = new BetType("Jugador?", e, false);
			List<Option> opts = createoptions();
			Long id = e.getId();
			optionService.create(bet, opts, id);
			List<BetType> bets = new ArrayList<BetType>();
			bets.addAll(e.getBetTypes());
			for (int i = 0; i < bets.size(); i++) {
				BetType b = (BetType) bets.get(i);
				if (b.getQuestion().equals("Jugador?"))
					return b;
			}
		} catch (InstanceNotFoundException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	// PR-UN-022
	@Test
	public void testCreateBetAndFindBet() throws InstanceNotFoundException, DuplicateInstanceException,
			EventEmpezadoException, PastEventException, DuplicateEventException, BetTypesIgualesException {

		Category category = createCat();
		Event event = createEvent(category);
		BetType betType = createBetType(event);
		UserProfile user = createUser();
		Float apuesta = (float) 15;
		Bet bet = betService.createBet(betType.getOptions().get(0).getId(), user.getUserProfileId(), apuesta);
		Bet bet2 = betService.findBet(bet.getId());

		assertEquals(bet, bet2);
	}

	// PR-UN-023
	@Test
	public void testFindBetsByUserId() throws InstanceNotFoundException, DuplicateInstanceException,
			EventEmpezadoException, PastEventException, DuplicateEventException, BetTypesIgualesException {

		/*
		 * Create the accounts that should be found and store them in the
		 * correct order (we assume that the account identifiers are generated
		 * in ascending order; in other case the list should be ordered by the
		 * account identifiers).
		 */
		int numberOfBets = 11;
		List<Bet> expectedBets = new ArrayList<Bet>();
		Category category = createCat();
		Event event = createEvent(category);
		BetType betType = createBetType(event);
		UserProfile user = createUser();
		for (int i = 0; i < numberOfBets; i++) {
			expectedBets.add(
					betService.createBet(betType.getOptions().get(0).getId(), user.getUserProfileId(), (float) 15.00));
		}

		UserProfile user2 = userService.registerUser("user2", "pword",
				new UserProfileDetails("nae", "lnme2", "nme@email.es"));
		// Create an account that should not be found.
		betService.createBet(betType.getOptions().get(2).getId(), user2.getUserProfileId(), (float) 15.00);

		BetBlock betBlock;
		int count = 10;
		int startIndex = 0;

		// Check account retrieval.
		short resultIndex = 0;
		do {
			betBlock = betService.findBetsByUserId(user.getUserProfileId(), startIndex, count);
			assertTrue(betBlock.getBets().size() <= count);
			for (Bet bet : betBlock.getBets()) {
				assertTrue(expectedBets.contains(bet));
			}
			startIndex += count;

		} while (betBlock.getExistMoreBets());

		assertTrue(numberOfBets == startIndex - count + betBlock.getBets().size());

	}

	// PR-UN-024
	@Test(expected = InstanceNotFoundException.class)
	public void testCreateBetAndFindBet2() throws InstanceNotFoundException, DuplicateInstanceException,
			EventEmpezadoException, PastEventException, DuplicateEventException, BetTypesIgualesException {

		Bet bet = betService.createBet(123456789L, 3L, 6F);
	}

	// PR-UN-025
	@Test(expected = InstanceNotFoundException.class)
	public void testCreateBetAndFindBet3() throws InstanceNotFoundException, DuplicateInstanceException,
			EventEmpezadoException, PastEventException, DuplicateEventException, BetTypesIgualesException {

		Category category = createCat();
		Event event = createEvent(category);
		BetType betType = createBetType(event);
		UserProfile user = createUser();
		Float apuesta = (float) 15;
		Bet bet = betService.createBet(betType.getOptions().get(0).getId(), 67L, apuesta);
	}

}
