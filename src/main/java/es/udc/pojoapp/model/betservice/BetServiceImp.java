package es.udc.pojoapp.model.betservice;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.pojoapp.model.bet.Bet;
import es.udc.pojoapp.model.bet.BetDao;
import es.udc.pojoapp.model.bettypeservice.EventEmpezadoException;
import es.udc.pojoapp.model.option.Option;
import es.udc.pojoapp.model.option.OptionDao;
import es.udc.pojoapp.model.userprofile.UserProfile;
import es.udc.pojoapp.model.userprofile.UserProfileDao;

@Service("betService")
@Transactional
public class BetServiceImp implements BetService{

	@Autowired
	private BetDao betDao;
	@Autowired
	private OptionDao optionDao;
	@Autowired
	private UserProfileDao userProfileDao;
	
	@Override
	@Transactional
	public Bet createBet(Long optionId, Long userId, Float cantidad) throws InstanceNotFoundException, EventEmpezadoException {
		Option option = optionDao.find(optionId);
		UserProfile user = userProfileDao.find(userId); 
		if (option.getBetType().getEvent().getDate().after(Calendar.getInstance()) == true){
			Bet bet = new Bet(option, cantidad, user, Calendar.getInstance());
			betDao.save(bet);
			return bet;
		}
		else
			throw new EventEmpezadoException("pene");
	}
	
	/*@Override
	@Transactional
	public void removeBet(Long betId) throws InstanceNotFoundException{
		betDao.remove(betId);
	}*/
	
	//solo usada para los test
	@Override
	@Transactional(readOnly = true)
	public Bet findBet(Long betId) throws InstanceNotFoundException{
		return betDao.find(betId);
	}

	@Override
	@Transactional(readOnly = true)
	public BetBlock findBetsByUserId(Long userId, int startIndex, int count) {
		/*
		 * Find count+1 accounts to determine if there exist more accounts above
		 * the specified range.
		 */
		List<Bet> bets = betDao.findUserBets(userId, startIndex,
					count + 1);

		boolean existMoreBets = bets.size() == (count + 1);

		/*
		 * Remove the last account from the returned list if there exist more
		 * accounts above the specified range.
		 */
		if (existMoreBets) {
			bets.remove(bets.size() - 1);
		}

		/* Return AccountBlock. */
		return new BetBlock(bets, existMoreBets);
	}
}
