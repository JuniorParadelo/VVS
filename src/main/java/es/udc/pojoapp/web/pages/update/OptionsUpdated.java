package es.udc.pojoapp.web.pages.update;


import es.udc.pojoapp.web.services.AuthenticationPolicy;
import es.udc.pojoapp.web.services.AuthenticationPolicyType;

@AuthenticationPolicy(AuthenticationPolicyType.ADMIN_USERS)
public class OptionsUpdated {

	private String result;
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	String onPassivate() {
		return result;
	}
	
	void onActivate(String result) {
		this.result = result;
	}
	
}
