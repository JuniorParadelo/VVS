package es.udc.pojoapp.model.option;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

@Repository("optionDao")
public class OptionDaoHibernate extends GenericDaoHibernate<Option, Long> implements OptionDao {

	/*@Override
	public Long findOptionIdByName(String name) {
		return (Long) getSession().createQuery(
				"SELECT o.id FROM Option o WHERE o.name = :name ").
				setParameter("name", name).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Option> findOptionsBetType(Long betTypeId) {
		return getSession().createQuery(
				"SELECT o FROM Option o WHERE o.betTypeId =:betId ").
				setParameter("betId", betTypeId).list();
	}*/

}
