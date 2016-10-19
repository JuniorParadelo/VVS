package es.udc.pojoapp.model.eventservice;
	

@SuppressWarnings("serial")
public class DuplicateEventException extends Exception {
	
	private String eventName;
	
	public DuplicateEventException(String eventName) {
		super("Event "+eventName+" already exists");
		this.eventName=eventName;
	}
	
	public String getEventName() {
		return this.eventName;
	}

}
