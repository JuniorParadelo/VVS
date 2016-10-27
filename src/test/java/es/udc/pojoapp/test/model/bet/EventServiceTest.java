package es.udc.pojoapp.test.model.bet;

import static es.udc.pojoapp.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.udc.pojoapp.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.pojoapp.model.category.Category;
import es.udc.pojoapp.model.event.Event;
import es.udc.pojoapp.model.event.EventDao;
import es.udc.pojoapp.model.eventservice.DuplicateEventException;
import es.udc.pojoapp.model.eventservice.EventBlock;
import es.udc.pojoapp.model.eventservice.EventService;
import es.udc.pojoapp.model.eventservice.PastEventException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class EventServiceTest {

	@Autowired
	private EventService eventService;

	@Autowired
	private EventDao eventDao;

	private Category createCat() {
		return eventService.createCategory("Futbol");
	}

	private Event defaultEvent(Category cat) throws PastEventException, DuplicateEventException {
		Calendar date = Calendar.getInstance();
		date.set(Calendar.HOUR, 20);
		date.set(Calendar.MINUTE, 45);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		date.add(Calendar.DAY_OF_MONTH, 2);
		return eventService.createEvent("Deportivo - Real Madrid", date, cat.getIdentificador());
	}

	// PR-UN-014
	@Test
	public void CreateCategory() throws InstanceNotFoundException {
		Category cat = eventService.createCategory("Futbol");
		Category cate = eventService.findCategory(cat.getIdentificador());

		assertEquals(cat, cate);
	}

	// PR-UN-015
	@Test
	public void CreateAndFindTest() throws InstanceNotFoundException, PastEventException, DuplicateEventException {

		Category cat = createCat();
		Event event1 = defaultEvent(cat);
		Event event2 = eventService.findEvent(event1.getId());

		assertEquals(event1, event2);
	}

	// PR-UN-016
	@Test
	public void FindEventsbyKeywordsTest()
			throws InstanceNotFoundException, PastEventException, DuplicateEventException {

		Category cat = createCat();
		Event event = defaultEvent(cat);
		eventDao.save(new Event("Celta - Barcelona", Calendar.getInstance(), cat));
		EventBlock list = eventService.findEventbyKeywords("madrid depor", null, false, 0, 5);

		assertTrue(list.getEvents().contains(event) && list.getEvents().size() == 1);
	}

	// PR-UN-017
	@Test
	public void FindEventsbyCategoryTest()
			throws InstanceNotFoundException, PastEventException, DuplicateEventException {

		Category cat = createCat();
		Event event = defaultEvent(cat);
		eventDao.save(new Event("Celta - Barcelona", Calendar.getInstance(), eventService.createCategory("Waterpolo")));
		EventBlock list = eventService.findEventbyKeywords(null, cat.getIdentificador(), false, 0, 5);

		assertTrue(list.getEvents().contains(event) && list.getEvents().size() == 1);
	}

	// PR-UN-018
	@Test(expected = DuplicateEventException.class)
	public void DuplicateEventTest() throws PastEventException, DuplicateEventException {
		Category cat = createCat();
		defaultEvent(cat);
		defaultEvent(cat);
	}

	// PR-UN-019
	@Test(expected = PastEventException.class)
	public void PastEventTest() throws PastEventException, DuplicateEventException {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_MONTH, -5);
		eventService.createEvent("Deportivo - Real Madrid", date, createCat().getIdentificador());
	}

	// PR-UN-020
	@Test(expected = InstanceNotFoundException.class)
	public void CreateFailFindTest() throws InstanceNotFoundException, PastEventException, DuplicateEventException {

		Event event2 = eventService.findEvent(69L);

	}

	// PR-UN-021
	@Test(expected = InstanceNotFoundException.class)
	public void CreateCategoryError() throws InstanceNotFoundException {
		Category cate = eventService.findCategory(100L);

	}

}
