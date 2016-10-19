package es.udc.pojoapp.web.pages.create;


import es.udc.pojoapp.web.services.AuthenticationPolicy;
import es.udc.pojoapp.web.services.AuthenticationPolicyType;

@AuthenticationPolicy(AuthenticationPolicyType.ADMIN_USERS)
public class EventCreated {

	private Long eventId;
	
	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	Long onPassivate() {
		return eventId;
	}
	
	void onActivate(Long eventId) {
		this.eventId = eventId;
	}
	
}
