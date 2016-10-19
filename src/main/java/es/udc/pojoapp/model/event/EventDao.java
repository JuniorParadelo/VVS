package es.udc.pojoapp.model.event;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;

public interface EventDao extends GenericDao<Event, Long> {

	public List<Event> findEventsByKeywords(String keywords, Long categoria, Boolean admin, int startIndex,
			int count);
	
	public List<Event> findEventsByName(String keywords);
}

	
