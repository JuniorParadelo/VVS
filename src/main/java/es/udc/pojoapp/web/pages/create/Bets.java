package es.udc.pojoapp.web.pages.create;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.pojoapp.web.services.AuthenticationPolicy;
import es.udc.pojoapp.web.services.AuthenticationPolicyType;
import es.udc.pojoapp.web.util.UserSession;
import es.udc.pojoapp.model.bet.Bet;
import es.udc.pojoapp.model.betservice.BetService;
import es.udc.pojoapp.model.bettypeservice.BetTypeService;
import es.udc.pojoapp.model.bettypeservice.EventEmpezadoException;
import es.udc.pojoapp.model.event.Event;
import es.udc.pojoapp.model.eventservice.EventService;
import es.udc.pojoapp.model.option.Option;

@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class Bets {
	
	@Property
	@SessionState(create=false)
	private UserSession userSession;
	
	@Component
	private Form createBetForm;
	
	@Component(id="apuesta")
	private TextField apuestaTextField;
	
	@Property
	private String apuesta;
	
	private float apuestaAsFloat;
	
	private Long optionId;
	
	@Inject
	private Messages messages;
	
	@Inject
	private Locale locale;
	
	@Inject
	private BetTypeService betTypeService;
	
	@Inject
	private BetService betService;
	
	@InjectPage
	private BetCreated betCreated;
	
	public Option getOption() {
		try {
			return betTypeService.findOption(optionId);
		} catch (InstanceNotFoundException e) {
			return null;
		}
	}
	
	
	public Long getOptionId() {
		return optionId;
	}

	public void setOptionId(Long optionId) {
		this.optionId = optionId;
	}

	void onValidateFromCreateBetForm() {
		
		if (!createBetForm.isValid()) {
			return;
		}
		
		NumberFormat numberFormatter = NumberFormat.getInstance(locale);
		ParsePosition position = new ParsePosition(0);
		Number number = 
			numberFormatter.parse(apuesta, position);
		
		if (position.getIndex() != apuesta.length()) {
			createBetForm.recordError(apuestaTextField, messages.format(
					"error-incorrectNumberFormat", apuesta));
			return;
		} else {
			apuestaAsFloat = number.floatValue();
		}
		Bet bet = null;
		try {
			bet = betService.createBet(optionId, userSession.getUserProfileId(), apuestaAsFloat);
		} catch (InstanceNotFoundException e) {
			createBetForm.recordError(messages.get("error-notFound"));
			return;
		} catch (EventEmpezadoException e){
			createBetForm.recordError(messages.get("error-started"));
			return;
		}
		betCreated.setbetId(bet.getId());
	}
	
	Object onSuccess() {
		
		return betCreated;
	}
	
	
	void onActivate(Long optionId) {
		this.optionId = optionId;
	}
	
	Long onPassivate() {
		return optionId;
		}
	

}
