package es.udc.pojoapp.web.pages.create;

import es.udc.pojoapp.web.services.AuthenticationPolicy;
import es.udc.pojoapp.web.services.AuthenticationPolicyType;

@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class BetCreated {

	private Long betId;
	
	public Long getbetId() {
		return betId;
	}

	public void setbetId(Long betId) {
		this.betId = betId;
	}

	Long onPassivate() {
		return betId;
	}
	
	void onActivate(Long betId) {
		this.betId = betId;
	}

}
