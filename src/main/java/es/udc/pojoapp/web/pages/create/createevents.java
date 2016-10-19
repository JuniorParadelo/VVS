package es.udc.pojoapp.web.pages.create;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojoapp.model.category.Category;
import es.udc.pojoapp.model.event.Event;
import es.udc.pojoapp.model.eventservice.DuplicateEventException;
import es.udc.pojoapp.model.eventservice.EventService;
import es.udc.pojoapp.model.eventservice.PastEventException;
import es.udc.pojoapp.web.services.AuthenticationPolicy;
import es.udc.pojoapp.web.services.AuthenticationPolicyType;

@AuthenticationPolicy(AuthenticationPolicyType.ADMIN_USERS)
public class createevents {

	
	@Component
	private Form createEventForm;

	@Component(id="eventDate")
	private TextField eventDateField;
	
	@Component(id="nameEvent")
	private TextField nameField;
	
	@Property
	private String nameEvent;	
	
	@Property
	private String eventDate;
	
	@Property
	private String categoriaId;
	
	@Property
	private List<Category> categorys;
	
	Date eventDateAsDate;
	
	@Inject
	private Locale locale;
	
	@Inject
	private Messages messages;
	
	@Inject
	private EventService eventService;
	
	@InjectPage
	private EventCreated eventCreated;
	
	public String getCategorias(){
		String categorias = "";
		for(Category c: categorys){
			if (categorias.equals("")){
				categorias=categorias+c.getIdentificador().toString()+"="+c.getName();
			} else {
				categorias=categorias+","+c.getIdentificador().toString()+"="+c.getName();
			}
		}
		return categorias;
	}
	
	void onValidateFromCreateEventForm() {
		
		if (!createEventForm.isValid()) {
			return;
		}

		eventDateAsDate = validateDate(eventDateField, eventDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(eventDateAsDate);
		Event evento = null;
		try {
			evento = eventService.createEvent(nameEvent, calendar, Long.parseLong(categoriaId));
		} catch (PastEventException e) {
			createEventForm.recordError(messages.get("error-fecha"));
			return;
		} catch (DuplicateEventException e){
			createEventForm.recordError(messages.get("error-eventoDuplicado"));
			return;
		}
		eventCreated.setEventId(evento.getId());
		
	}
	
	Object onSuccess() {
		
		return eventCreated;
	}
	
	void onActivate() {
		categorys = eventService.findAllCategory();
		eventDate = dateToString(Calendar.getInstance().getTime());
	}
	

	private Date validateDate(TextField textField, String dateAsString) {
		
		ParsePosition position = new ParsePosition(0);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = sdf.parse(dateAsString, position);
		
		if (position.getIndex() != dateAsString.length()) {
			createEventForm.recordError(textField,
				messages.format("error-incorrectDateFormat", dateAsString));
		}

		return date;
		
	}
	
	private String dateToString(Date date) {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").
			format(date);
	}

}
