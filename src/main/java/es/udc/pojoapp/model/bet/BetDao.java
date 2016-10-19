package es.udc.pojoapp.model.bet;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;

public interface BetDao extends GenericDao<Bet, Long> {

	public List<Bet> findUserBets(Long user, int startIndex,
			int count); 
}
