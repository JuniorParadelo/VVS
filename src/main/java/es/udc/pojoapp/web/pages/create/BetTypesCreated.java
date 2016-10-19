package es.udc.pojoapp.web.pages.create;

import java.util.ArrayList;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojoapp.model.option.Option;
import es.udc.pojoapp.web.services.AuthenticationPolicy;
import es.udc.pojoapp.web.services.AuthenticationPolicyType;
import es.udc.pojoapp.web.util.UserSession;

@AuthenticationPolicy(AuthenticationPolicyType.ADMIN_USERS)
public class BetTypesCreated {
	
	@Property
	@SessionState(create=false)
	private UserSession userSession;
	
	@Inject
	private Messages messages;
	
	private String result;

	public boolean isError() {
		return result.equals("betId-label");
	}
	
	public String getResult() {
		return messages.get(result);
	}

	public void setResult(String result) {
		this.result = result;
	}

	String onPassivate() {
		return result;
	}
	
	void onActivate(String result) {
		this.result = result;
		userSession.setEventoId(null);
		userSession.setBetType(null);
		userSession.setOptions(new ArrayList<Option>());
		
	}
	
	

}
