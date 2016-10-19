package es.udc.pojoapp.web.pages.search;

import java.text.DateFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import es.udc.pojoapp.web.services.AuthenticationPolicy;
import es.udc.pojoapp.web.services.AuthenticationPolicyType;
import es.udc.pojoapp.web.util.UserSession;
import es.udc.pojoapp.model.bet.Bet;
import es.udc.pojoapp.model.betservice.BetBlock;
import es.udc.pojoapp.model.betservice.BetService;


@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class FindBets {

	private final static int ACCOUNTS_PER_PAGE = 10;

	//private Long userId;
	private int startIndex = 0;
	private BetBlock betBlock;
	private Bet bet;


	@Property
	@SessionState(create=false)
	private UserSession userSession;
		
	@Inject
	private BetService betService;
	
	@Inject
	private Locale locale;
	
	@InjectComponent
	private Zone betZone;
	
	@InjectComponent
	private Zone botonZone;
	
	@Inject
	private Request request;
	
	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;
	
	/*public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}*/
	
	public List<Bet> getBets() {
		return betBlock.getBets();
	}
	
	public boolean isPending() {
		return bet.getOption().getWin() == null;
	}
	
	
	public Bet getBet() {
		return bet;
	}

	public void setBet(Bet bet) {
		this.bet = bet;
	}
	
	public Format getFormatd() {
		return DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT,locale);
	}
	
	public Format getFormat() {
		return NumberFormat.getInstance(locale);
	}
	
	/*********************************************************************************/
	public boolean getPreviousLinkContext() {
		
		if (startIndex-ACCOUNTS_PER_PAGE >= 0) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public boolean getNextLinkContext() {	
		if (betBlock.getExistMoreBets()) {
			return true;
		} else {
			return false;
		}
		
	}
	
	void onActionFromAdelante(){
		this.startIndex=startIndex+ACCOUNTS_PER_PAGE;
		this.betBlock=betService.findBetsByUserId(userSession.getUserProfileId(), startIndex, ACCOUNTS_PER_PAGE);

		if (request.isXHR())
			ajaxResponseRenderer.addRender("botonZone", botonZone.getBody()).addRender("betZone",betZone.getBody());
	}
	
	void onActionFromAtras(){
		this.startIndex = startIndex-ACCOUNTS_PER_PAGE;
		this.betBlock = betService.findBetsByUserId(userSession.getUserProfileId(), startIndex, ACCOUNTS_PER_PAGE);
		
		if (request.isXHR())
			ajaxResponseRenderer.addRender("botonZone", botonZone.getBody()).addRender("betZone",betZone.getBody());
	}
	/*********************************************************************************/
	
	Object[] onPassivate() {
		return new Object[] {startIndex};
	}
	
	void onActivate(/*Long userId,*/ int startIndex) {
		/*this.userId = userId;*/
		this.startIndex = startIndex;
		betBlock = betService.findBetsByUserId(userSession.getUserProfileId(),
			startIndex, ACCOUNTS_PER_PAGE);
	}
}
