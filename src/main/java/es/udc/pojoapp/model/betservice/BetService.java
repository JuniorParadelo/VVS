package es.udc.pojoapp.model.betservice;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.pojoapp.model.bet.Bet;
import es.udc.pojoapp.model.bettypeservice.EventEmpezadoException;
import es.udc.pojoapp.model.userprofile.UserProfile;

public interface BetService {
	
	public Bet createBet(Long option, Long userId, Float cantidad)throws InstanceNotFoundException, EventEmpezadoException;
	
	//solo usada para los test
	public Bet findBet(Long betId) throws InstanceNotFoundException;
	
	//public void removeBet(Long betId) throws InstanceNotFoundException;
	
    public BetBlock findBetsByUserId(Long userId,
            int startIndex, int count);
	
	
}
