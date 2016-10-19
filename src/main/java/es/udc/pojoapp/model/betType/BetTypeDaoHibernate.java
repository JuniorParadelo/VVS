package es.udc.pojoapp.model.betType;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

@Repository("betType")
public class BetTypeDaoHibernate extends GenericDaoHibernate<BetType, Long>implements BetTypeDao {

	@Override
	public boolean findEqualsBetTypesEvent(String pregunta, Long eventId) {
		List<BetType> tipos = null;
		tipos = getSession().createQuery(
				"SELECT b FROM BetType b WHERE b.event.id =:eventId AND b.question LIKE :pregunta").
				setParameter("eventId", eventId).
				setParameter("pregunta",pregunta).list();
		if (tipos.size() != 0)
			return true;
		return false;
	}

	/*@Override
	public Long findBetTypeIdByName(String name) {
		return (Long) getSession().createQuery(
				"SELECT b.id FROM BetType b WHERE o.name = :name ").
				setParameter("name", name).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BetType> findBetTypeEvents(Long eventId) {
		return getSession().createQuery(
				"SELECT b FROM BetType b WHERE b.eventId =:eventId ").
				setParameter("eventId", eventId).list();
	}*/
}
