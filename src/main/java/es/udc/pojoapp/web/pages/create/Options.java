package es.udc.pojoapp.web.pages.create;

import java.text.Format;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.pojoapp.model.betType.BetType;
import es.udc.pojoapp.model.bettypeservice.BetTypeService;
import es.udc.pojoapp.model.bettypeservice.BetTypesIgualesException;
import es.udc.pojoapp.model.bettypeservice.EventEmpezadoException;
import es.udc.pojoapp.model.eventservice.EventService;
import es.udc.pojoapp.model.option.Option;
import es.udc.pojoapp.web.util.UserSession;

public class Options {

	@Component
	private Form createOptionForm;
	
	@Component
	private Form createBetTypesForm;
	
	
	@Property
	@SessionState(create=false)
	private UserSession userSession;
	
	@Property
	private String respuesta;
	
	@Property
	private String cuote;
	
	@Component(id="cuote")
	private TextField cuoteTextField;
	
	private Long eventId;
	private Option opcion;

	@Inject
	private Locale locale;
	
	@Inject
	private Messages messages;
	
	@Inject
	private BetTypeService betService;
	
	@Inject
	private EventService eventService;
	
	@Inject
	private Request request;
	
	@InjectPage
	private BetTypesCreated betTypes;
	
	@InjectComponent
	private Zone listaZone;
	
	@InjectComponent
	private Zone myZone;
	
    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;
	
	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	
	public Option getOption(){
		return opcion;
		
	}
	public void setOption(Option opcion){
		this.opcion = opcion;
		
	}
	
	public List<Option> getOptions(){
		return userSession.getOptions();
	}
	
	public Object getOpt(){
		if (userSession.getOptions().size()==0){
			return null;
		}
		return new Integer(1);
	}
	public BetType getBet(){
		if (userSession.getBetType() == null){
			return null;
		}
		return userSession.getBetType();
	}
	
	public Format getFormat() {
		return NumberFormat.getInstance(locale);
	}

	void onValidateFromCreateBetTypesForm() {
		
		if (!createBetTypesForm.isValid()) {
			return;
		}
			try {
				betService.create(userSession.getBetType(), userSession.getOptions(), eventId);
			} catch (InstanceNotFoundException e){
				createBetTypesForm.recordError(messages.get("evento-no-existente"));
			} catch (EventEmpezadoException e){
				createBetTypesForm.recordError(messages.get("evento-empezado"));
			} catch (BetTypesIgualesException e) {
				createBetTypesForm.recordError(messages.get("betType-igual"));
			}
	}
		
		
	void onValidateFromCreateOptionForm() {
		
		if (!createOptionForm.isValid()) {
			return;
		}
		
		if (respuesta.equals("Option") || respuesta.equals("Opcion") ){
			createOptionForm.recordError(messages.get("error-respuesta"));
			return;
		}
				
		Double cuota = null;
		NumberFormat numberFormatter = NumberFormat.getInstance(locale);
		ParsePosition position = new ParsePosition(0);
		Number number = numberFormatter.parse(cuote, position);
			
		if (position.getIndex() != cuote.length()) {
			createOptionForm.recordError(cuoteTextField, messages.format(
						"error-incorrectNumberFormat", cuote));
			return;
		} else {
			cuota = number.doubleValue();
			
			Option opcion = new Option(respuesta, cuota, null);
			
			userSession.getOptions().add(opcion);
			
			if (request.isXHR()) 
				 ajaxResponseRenderer.addRender(myZone).addRender(listaZone);
		}

	}
	
	Object onSuccessFromCreateBetTypesForm() {
		
			userSession.setOptions(new ArrayList<Option>());
			userSession.setBetType(null);
			return betTypes;
	}
	
	void onActivate(Long eventId) {
		this.eventId = eventId;
	}
	
	Long onPassivate() {
		return eventId;
	}

}

