package es.udc.pojoapp.web.pages.update;

import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.internal.services.StringValueEncoder;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.pojoapp.model.betType.BetType;
import es.udc.pojoapp.model.bettypeservice.BetTypeService;
import es.udc.pojoapp.model.bettypeservice.EventoNoEmpezadoException;
import es.udc.pojoapp.model.bettypeservice.OpcionesActualizadasException;
import es.udc.pojoapp.model.bettypeservice.UnicaGanadoraException;
import es.udc.pojoapp.model.event.Event;
import es.udc.pojoapp.model.eventservice.EventService;
import es.udc.pojoapp.model.option.Option;
import es.udc.pojoapp.web.services.AuthenticationPolicy;
import es.udc.pojoapp.web.services.AuthenticationPolicyType;
import es.udc.pojoapp.web.util.UserSession;

@AuthenticationPolicy(AuthenticationPolicyType.ADMIN_USERS)
public class BetTypes {
	
	@Property
	@SessionState(create=false)
	private UserSession userSession;
	
	@Property
	private final StringValueEncoder stringValueEncoder = new StringValueEncoder();
	
	@InjectPage
	OptionsUpdated optionsUpdated;
	
	
	@Inject
	private EventService eventService;
	
	@Inject
	private BetTypeService betTypeService;
	
	@Component
	private Form updateBetTypeForm;
	
	@Property
	private List<String> checklistSelectedValues;
	
			
	private Calendar fecha;
	
	@Property
	private BetType betType;
	
	@Property
	private Option option;
	
	@Inject
	private Messages messages;
	
	@Inject
	private Locale locale;

	private Long betTypeId;
	
	public Format getFormat() {
		return NumberFormat.getInstance(locale);
	}	
	
	public boolean getFecha(){
		if (Calendar.getInstance().after(fecha)){
			return false;
		}
		return true;
	}

	public List<Option> getOptions(BetType betType) {
		return betType.getOptions();
	}
	
	public String[] getListvalues() {
		List<Option> opts = betType.getOptions();
		String[] list = new String[opts.size()];
		int cont = 0;
		for (Option o: opts) {
			list[cont] = o.getName();
			cont++;
		}
		return list;
	}
	
	void onValidateFromUpdateBetTypeForm() {
		
		if (!updateBetTypeForm.isValid()) {
			return;
		}
		List<Long> options = checkOptions();
		try {
			betTypeService.updateState(betTypeId, options);
		} catch (OpcionesActualizadasException e) {
			updateBetTypeForm.recordError(messages.get("opciones-ya-actualizadas"));
			return;
		} catch (UnicaGanadoraException e) {
			updateBetTypeForm.recordError(messages.get("opcion-unica-ganadora"));
			return;
		} catch (EventoNoEmpezadoException e) {
			updateBetTypeForm.recordError(messages.get("evento-no-empezado"));
			return;
		} catch (Exception e) {
			updateBetTypeForm.recordError(messages.get("ERROR"));
			return;
		}
	}
	
	public List<Long> checkOptions() {
		List<Long> opts = new ArrayList<Long>();
		for (Option o : betType.getOptions()) {
			if (checklistSelectedValues.contains(o.getName())) {
				opts.add(o.getId());
			}
		}
		return opts;
	}
	
	Object onSuccess() {		
		optionsUpdated.setResult("Opciones actualizadas, ID = "+betTypeId);
		return optionsUpdated;		
	}
	
	void onActivate(Long betTypeId) {
		this.betTypeId = betTypeId;
		try {
			this.betType = betTypeService.findBetType(betTypeId);
			Event event = eventService.findEvent(betType.getEvent().getId());
			fecha = event.getDate();
		} catch (InstanceNotFoundException e) {
		}
		
	}
	
	Long onPassivate() {
		return betTypeId;
		}
}
