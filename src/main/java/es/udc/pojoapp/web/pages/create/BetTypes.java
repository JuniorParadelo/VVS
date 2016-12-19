package es.udc.pojoapp.web.pages.create;

import java.text.Format;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Calendar;
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
import es.udc.pojoapp.model.event.Event;
import es.udc.pojoapp.model.eventservice.EventService;
import es.udc.pojoapp.model.option.Option;
import es.udc.pojoapp.web.services.AuthenticationPolicy;
import es.udc.pojoapp.web.services.AuthenticationPolicyType;
import es.udc.pojoapp.web.util.UserSession;

@AuthenticationPolicy(AuthenticationPolicyType.ADMIN_USERS)
public class BetTypes {

	public enum Unica {
		NO, YES
	}

	private Long eventId;
	private Option opcion;
	private Double cuota;

	@Property
	private Unica unica;

	@Property
	private String respuesta;

	@Property
	private String cuote;

	@Component(id = "cuote")
	private TextField cuoteTextField;

	@Component
	private Form createBetTypeForm;

	@Component
	private Form createOptionForm;

	@Component
	private Form createBetTypesForm;

	@Property
	@SessionState(create = false)
	private UserSession userSession;

	@Property
	private String question;

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
	private Zone betZone;

	@InjectComponent
	private Zone optionZone;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	public Event getCheck() {
		try {
			Event evento = eventService.findEvent(eventId);
			return evento;
		} catch (InstanceNotFoundException e) {
			return null;
		}

	}

	public boolean getFecha() {
		Event evento;
		try {
			evento = eventService.findEvent(eventId);
			if (Calendar.getInstance().after(evento.getDate())) {
				return false;
			}
			return true;
		} catch (InstanceNotFoundException e) {
			return false;
		}

	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	void onValidateFromCreateBetTypeForm() {
		if (!createBetTypeForm.isValid()) {
			return;
		}

		boolean unicidad;
		if (unica == Unica.NO)
			unicidad = false;
		else
			unicidad = true;

		userSession.setBetType(new BetType(question, null, unicidad));

		if (request.isXHR()) {
			ajaxResponseRenderer.addRender("betZone", betZone.getBody()).addRender("optionZone", optionZone.getBody());
		}

	}

	/* Parte de las opciones */
	/***************************************************************************************************/
	public Option getOption() {
		return opcion;

	}

	public void setOption(Option opcion) {
		this.opcion = opcion;

	}

	public List<Option> getOptions() {
		return userSession.getOptions();
	}

	public Object getOpt() {
		if (userSession.getOptions().size() == 0) {
			return null;
		}
		return new Integer(1);
	}

	public boolean getBet() {
		if (userSession.getBetType() == null) {
			return false;
		} else {
			return true;
		}
	}

	public Format getFormat() {
		return NumberFormat.getInstance(locale);
	}

	Object onValidateFromCreateBetTypesForm() {

		try {
			betService.create(userSession.getBetType(), userSession.getOptions(), eventId);

		} catch (InstanceNotFoundException e) {
			createBetTypesForm.recordError(messages.get("evento-no-existente"));
			betTypes.setResult("evento-no-existente");
			return betTypes;
		} catch (EventEmpezadoException e) {
			createBetTypesForm.recordError(messages.get("evento-empezado"));
			betTypes.setResult("evento-empezado");
			return betTypes;
		} catch (BetTypesIgualesException e) {
			createBetTypesForm.recordError(messages.get("betType-igual"));
			betTypes.setResult("betType-igual");
			return betTypes;
		}
		betTypes.setResult("betId-label");
		return betTypes;
	}

	void onValidateFromCreateOptionForm() {

		if (!createOptionForm.isValid()) {
			return;
		}

		if (respuesta.equals("Option") || respuesta.equals("Opcion")) {
			createOptionForm.recordError(messages.get("error-respuesta"));
			return;
		}

		NumberFormat numberFormatter = NumberFormat.getInstance(locale);
		ParsePosition position = new ParsePosition(0);
		Number number = numberFormatter.parse(cuote, position);

		if (position.getIndex() != cuote.length()) {
			createOptionForm.recordError(cuoteTextField, messages.format("error-incorrectNumberFormat", cuote));
			ajaxResponseRenderer.addRender("optionZone", optionZone.getBody());
		} else {
			cuota = number.doubleValue();

			Option opcion = new Option(respuesta, cuota, null);

			userSession.getOptions().add(opcion);

			if (request.isXHR()) {
				ajaxResponseRenderer.addRender("optionZone", optionZone.getBody()).addRender("listaZone",
						listaZone.getBody());
			}
		}

	}

	/***************************************************************************************************/
	/**
	 * findbugs bug fixed changing != comparision of Long to compareTo
	 */
	void onActivate(Long eventId) {
		this.eventId = eventId;

		if (userSession.getEventoId() == null)
			userSession.setEventoId(eventId);
		else if (userSession.getEventoId().compareTo(eventId) != 0) {
			userSession.setBetType(null);
			userSession.setOptions(new ArrayList<Option>());
			userSession.setEventoId(eventId);
		}
	}

	Long onPassivate() {
		return eventId;
	}

}
