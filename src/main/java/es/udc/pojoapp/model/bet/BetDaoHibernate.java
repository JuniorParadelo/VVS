package es.udc.pojoapp.model.bet;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

@Repository("betDao")
public class BetDaoHibernate extends
	GenericDaoHibernate<Bet, Long> implements BetDao {
	
	@SuppressWarnings("unchecked")
	public List<Bet> findUserBets(Long user, int startIndex,
	int count) {
	return getSession().createQuery(
	"SELECT b FROM Bet b WHERE b.userId.userProfileId = :userId " +
	"ORDER BY b.date desc").
	setParameter("userId", user).
	setFirstResult(startIndex).
	setMaxResults(count).list();
	}
}
