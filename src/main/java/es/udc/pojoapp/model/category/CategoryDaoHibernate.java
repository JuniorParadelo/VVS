package es.udc.pojoapp.model.category;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

@Repository("categoryDao")
public class CategoryDaoHibernate extends
	GenericDaoHibernate<Category, Long> implements CategoryDao{

	
	
	/*@Override
	public Long findCategoryByName(String name) {
		return (Long) getSession().createQuery(
				"SELECT c.categoryId FROM Category c WHERE c.name =:name ").
				setParameter("name", name).uniqueResult();
	}*/

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> findAllCategory() {
		return  getSession().createQuery("SELECT c FROM Category c ORDER BY c.name")
				.list();
	}
}
