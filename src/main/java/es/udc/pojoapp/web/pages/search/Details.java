package es.udc.pojoapp.web.pages.search;

import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.pojoapp.model.betType.BetType;
import es.udc.pojoapp.model.event.Event;
import es.udc.pojoapp.model.option.Option;
import es.udc.pojoapp.model.eventservice.EventService;
import es.udc.pojoapp.web.pages.create.Bets;
import es.udc.pojoapp.web.util.UserSession;

public class Details {
	
	@Property
	@SessionState(create=false)
	private UserSession userSession;
	
	@Inject
	private EventService eventService;
	
	@SessionState(create=false)
	private Long optionId;
	
	private Long eventId;
	
	private Event event;
	
	@Property
	private boolean version;
	
	private Calendar fecha;
	
	@Property
	private BetType betType;
	
	@Property
	private Option option;
	
	@Inject
	private Locale locale;
	
	@InjectPage
	private Bets apostar;
	
	
	public Event getEvent(){
		return event;
	}
	
	public Format getFormat() {
		return NumberFormat.getInstance(locale);
	}
	
	public boolean getVersion(BetType betType) {
		Option o = betType.getOptions().get(0);
		if (o.getWin() == null)
			return true;
		else
			return false;
		
	}
	
	public boolean getFecha(){
		if (Calendar.getInstance().after(fecha)){
			return false;
		}
		return true;
	}
	
	public List<BetType> getBetTypes() {
		List bets = new ArrayList<BetType>();
		bets.addAll(event.getBetTypes());
		return bets;
	}

	public List<Option> getOptions(BetType betType) {
		return betType.getOptions();
	}
	
	Object onActionFromApostar(long optionId){
		this.optionId = optionId;
		apostar.setOptionId(optionId);
		return apostar;
	}
	
	Object onActionFromApostar2(long optionId){
		apostar.setOptionId(optionId);
		return apostar;
	}

	void onActivate(Long eventId) {
		this.eventId = eventId;
		try {
		event = eventService.findEvent(eventId);
		} catch (InstanceNotFoundException e) {
		}
		fecha = event.getDate();
	}
	
	Long onPassivate() {
		return eventId;
		}

}
