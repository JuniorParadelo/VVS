package es.udc.pojoapp.web.pages.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.Messages;

import es.udc.pojoapp.model.category.Category;
import es.udc.pojoapp.model.event.Event;
import es.udc.pojoapp.model.eventservice.EventBlock;
import es.udc.pojoapp.model.eventservice.EventService;
import es.udc.pojoapp.web.util.UserSession;

public class FindEvents {

	@Property
	@SessionState(create=false)
	private UserSession userSession;
	
	@Component
	private Form findEventForm;
	
	@Property
	private String nameEvent;
	
	@Property
	private String categoriaId;
	
	@Property
	private List<Category> categorys;
	
	@Inject
	private Messages messages;
	
	@Inject
	private EventService eventService;
	
	@InjectPage
	private Events events;
	
	
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

	List<String> onProvideCompletionsFromNameEvent(String partial) {

		EventBlock eventos;
		boolean admin = false;
		try {
			if (userSession.isAdmin() == true)
				admin = userSession.isAdmin();
		} catch(NullPointerException e) {
			admin = false;
		}
		eventos = eventService.findEventbyKeywords(partial, null, admin, 0, 100);
		
		List<String> result = new ArrayList<String>();
		 
		for (Event a : eventos.getEvents()){
		    result.add(a.getName());
		}
		
		return result;
		
	}
	
	void onValidateFromFindEventForm() {
		
		if (!findEventForm.isValid()) {
			return;
		}
	}
	
	Object onSuccess() {
		if(categoriaId==null){
			events.setCategory(null);
		} else {
			events.setCategory(Long.parseLong(categoriaId));
		}
		if (nameEvent==null || nameEvent.equals("Palabras clave") || nameEvent.equals("keywords") || nameEvent.equals(" ") || nameEvent.equals("Keywords")){
			events.setKeywords(null);
		}	else {
			events.setKeywords(nameEvent);
		}
		if (userSession==null){
			events.setAdmin(false);
		} else {
			events.setAdmin(userSession.isAdmin());
		}
		return events;
		
	}
	
	void onActivate() {
		categorys = eventService.findAllCategory();
	}

}
