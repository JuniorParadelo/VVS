package es.udc.pojoapp.model.category;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;

public interface CategoryDao extends GenericDao<Category, Long> {
	
	//public Long findCategoryByName(String name);
	
	public List<Category> findAllCategory();
}
