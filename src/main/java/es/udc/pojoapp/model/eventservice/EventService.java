package es.udc.pojoapp.model.eventservice;

import java.util.Calendar;
import java.util.List;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.pojoapp.model.category.Category;
import es.udc.pojoapp.model.event.Event;

public interface EventService {
	
	public Event createEvent(String name, Calendar date, Long category) throws PastEventException, DuplicateEventException;
	
	public Event findEvent(Long Id) throws InstanceNotFoundException;
	
	public void removeEvent(Long id) throws InstanceNotFoundException;
	
	public EventBlock findEventbyKeywords(String keywords, Long category,
			Boolean admin,int startIndex, int count);
		
	public Category createCategory(String name);
	
	public Category findCategory(Long Id) throws InstanceNotFoundException;
	
	public void removeCategory(Long id) throws InstanceNotFoundException;
	
	public List<Category> findAllCategory();

	List<Event> findEventbyName(String keywords);
	
	//no usada
	//public Long findCategoryByName(String name);
}
