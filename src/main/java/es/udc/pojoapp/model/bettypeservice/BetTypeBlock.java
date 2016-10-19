package es.udc.pojoapp.model.bettypeservice;

import java.util.List;

import es.udc.pojoapp.model.betType.BetType;

public class BetTypeBlock {
	 private List<BetType> betTypes;
	    private boolean existMore;

	    public BetTypeBlock(List<BetType> betTypes, boolean existMore) {
	        
	        this.betTypes = betTypes;
	        this.existMore = existMore;

	    }
	    
	    public List<BetType> getBets() {
	        return betTypes;
	    }
	    
	    public boolean getExistMore() {
	        return existMore;
	    }
}
