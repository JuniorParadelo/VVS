package es.udc.pojoapp.model.eventservice;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.pojoapp.model.category.Category;
import es.udc.pojoapp.model.category.CategoryDao;
import es.udc.pojoapp.model.event.Event;
import es.udc.pojoapp.model.event.EventDao;

@Service("eventService")
public class EventServiceImpl implements EventService {

	@Autowired
	private EventDao eventDao;
	
	@Autowired 
	private CategoryDao categoryDao;
	
	@Override
	public Event createEvent(String name,Calendar date, Long category) throws PastEventException, DuplicateEventException {
		if (date.after(Calendar.getInstance())) {
			List<Event> events = eventDao.findEventsByKeywords(name.toUpperCase(), category,true, 0, 10);
			for (Event e: events) {
				if (e.getDate().equals(date))
					throw new DuplicateEventException(name);
			}
			Category cat = null;
			try {
				cat = categoryDao.find(category);
			} catch (InstanceNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Event event = new Event(name,date,cat);
			eventDao.save(event);
			return event;
		}
		else throw new PastEventException(name);
	}

	@Override
	@Transactional(readOnly = true)
	public Event findEvent(Long Id) throws InstanceNotFoundException {
		return eventDao.find(Id);		
	}

	@Override
	public void removeEvent(Long id) throws InstanceNotFoundException {
		eventDao.remove(id);
	}

	@Override
	@Transactional(readOnly = true)
	public EventBlock findEventbyKeywords(String keywords, Long category,
			Boolean admin,int startIndex, int count) {
		List<Event> list = eventDao.findEventsByKeywords(keywords, category,
				admin,startIndex, count+1);
		
		boolean existsMore = list.size() == (count+1);
		
		if(existsMore) {
			list.remove(list.size()-1);
		}
		
		return new EventBlock(list, existsMore);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Event> findEventbyName(String keywords){
		return eventDao.findEventsByName(keywords);
	}
	
	@Override
	public Category createCategory(String name) {
		Category cat = new Category();
		cat.setName(name);
		categoryDao.save(cat);
		return cat;
	}

	@Override
	public Category findCategory(Long Id) throws InstanceNotFoundException {
		return categoryDao.find(Id);
	
	}

	@Override
	public void removeCategory(Long id) throws InstanceNotFoundException {
		categoryDao.remove(id);
	}

	//este y el otro metodo implementado podrian usarse en alguna funcion de evento
	// y no ser necesarios como metodos del servicio
	@Override
	public List<Category> findAllCategory() {
		return categoryDao.findAllCategory();
	}

	//no usada
	/*@Override
	public Long findCategoryByName(String name) {
		return categoryDao.findCategoryByName(name);
	}*/

}
