package es.udc.pojoapp.model.option;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;

public interface OptionDao extends GenericDao<Option, Long>{
	
	//public Long findOptionIdByName(String name);
	//public List<Option>findOptionsBetType(Long betTypeId);
}
