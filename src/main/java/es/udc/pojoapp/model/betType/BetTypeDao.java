package es.udc.pojoapp.model.betType;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;

public interface BetTypeDao extends GenericDao<BetType, Long>{
	
	public boolean findEqualsBetTypesEvent(String pregunta, Long eventId);
	
	//public Long findBetTypeIdByName(String name);

	//public List<BetType> findBetTypeEvents(Long eventId);
}
