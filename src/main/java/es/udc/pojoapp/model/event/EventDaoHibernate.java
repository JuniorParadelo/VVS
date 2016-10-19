package es.udc.pojoapp.model.event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;


@Repository("eventDao")
public class EventDaoHibernate extends
GenericDaoHibernate<Event, Long> implements EventDao {

	DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	@SuppressWarnings("unchecked")
	public List<Event> findEventsByName(String keywords){
		int i = 0;
		String query = "SELECT e FROM Event e";		
		if (keywords != null) {
			String[] words = keywords.split(" ");
			query=query + (" WHERE UPPER(e.name) LIKE UPPER(?)");
			for(i=1;i<words.length;i++) {
				System.out.println(words[i]);
				query = query + " AND UPPER(e.name) LIKE UPPER(?)";
			}
		}
		query = query + (" ORDER BY e.name");
		Query q = getSession().createQuery(query);
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Event> findEventsByKeywords(String keywords, Long categoria,
			Boolean admin, int startIndex, int count) {
		int i = 0;
		String query = "SELECT e FROM Event e";		
		if (keywords != null) {
			String[] words = keywords.split(" ");
			query=query + (" WHERE UPPER(e.name) LIKE UPPER(?)");
			for(i=1;i<words.length;i++) {
				System.out.println(words[i]);
				query = query + " AND UPPER(e.name) LIKE UPPER(?)";
			}
		}
		if (categoria != null) {
			if (keywords == null)
				query = query + (" WHERE e.category.categoryId = :category");
			else
				query = query + (" AND e.category.categoryId = :category");
		}
		if (admin == false) {
			if (categoria == null && keywords == null) {
				query = query + (" WHERE e.date > :date");
			}
			else
				query = query + (" AND e.date > :date");
		}
		query = query + (" ORDER BY e.date");
		
		Query q = getSession().createQuery(
		query).
				setFirstResult(startIndex).
				setMaxResults(count);
		
		if (keywords != null) {
			String[]  words = keywords.split(" ");
			for(int j = 0; j<words.length;j++)
				q.setParameter(j, "%"+words[j]+"%");
		}
		if (categoria != null)
			q.setParameter("category", categoria);
		if (admin == false)
			q.setParameter("date", Calendar.getInstance());
		return q.list();
	}

}
