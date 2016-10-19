package es.udc.pojoapp.model.eventservice;

@SuppressWarnings("serial")
public class PastEventException extends Exception {
	
	private String eventName;
	
	public PastEventException(String eventName) {
		super("Event "+eventName+" with wrong date");
		this.eventName=eventName;
	}
	
	public String getEventName() {
		return this.eventName;
	}

}
