package es.udc.pojoapp.web.util;

import java.util.ArrayList;
import java.util.List;

import es.udc.pojoapp.model.betType.BetType;
import es.udc.pojoapp.model.option.Option;


public class UserSession {

	private Long userProfileId;
	private String firstName;
	private boolean admin;
	private Long eventoId;
	public Long getEventoId() {
		return eventoId;
	}

	public void setEventoId(Long eventoId) {
		this.eventoId = eventoId;
	}

	private BetType betType = null;
	private List<Option> options = new ArrayList<Option>();
	private Long optionId;

	public Long getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(Long userProfileId) {
		this.userProfileId = userProfileId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public BetType getBetType() {
		return betType;
	}

	public void setBetType(BetType betType) {
		this.betType = betType;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public Long getOptionId() {
		return optionId;
	}

	public void setOptionId(long optionId) {
		this.optionId = optionId;
	}

}
