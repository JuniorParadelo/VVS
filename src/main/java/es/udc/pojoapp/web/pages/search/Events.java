package es.udc.pojoapp.web.pages.search;

import java.text.DateFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import es.udc.pojoapp.model.event.Event;
import es.udc.pojoapp.model.eventservice.EventBlock;
import es.udc.pojoapp.model.eventservice.EventService;
import es.udc.pojoapp.web.util.UserSession;

public class Events {
		
		private final static int ACCOUNTS_PER_PAGE = 10;
		private int startIndex = 0;
		private EventBlock eventBlock;
		private Event event;
		private Long category;
		private String keywords;
		private boolean admin;

		@Inject
		private EventService eventService;
		
		@Inject
		private Locale locale;
		
		@InjectComponent
		private Zone eventZone;
		
		@InjectComponent
		private Zone botonZone;
		
		@Inject
		private Request request;
		
		@Inject
		private AjaxResponseRenderer ajaxResponseRenderer;
		
		public List<Event> getEvents() {
			return eventBlock.getEvents();
		}
		
		public Event getEvent() {
			return event;
		}

		public void setEvent(Event event) {
			this.event = event;
		}
		
		public Format getFormat() {
			return DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT,locale);
		}
		
		
		public Long getCategory() {
			return category;
		}

		public void setCategory(Long category) {
			this.category = category;
		}

		public String getKeywords() {
			return keywords;
		}

		public void setKeywords(String keywords) {
			this.keywords = keywords;
		}

		public boolean isAdmin() {
			return admin;
		}

		public void setAdmin(boolean admin) {
			this.admin = admin;
		}

		public boolean getPreviousLinkContext() {
			
			if (startIndex-ACCOUNTS_PER_PAGE >= 0) {
				return true;
			} else {
				return false;
			}
			
		}
		
		public boolean getNextLinkContext() {	
			if (eventBlock.getExistMoreEvents()) {
				return true;
			} else {
				return false;
			}
			
		}
		
		void onActionFromAdelante(){
			this.startIndex=startIndex+ACCOUNTS_PER_PAGE;
			this.eventBlock=eventService.findEventbyKeywords(keywords, category, admin, startIndex, ACCOUNTS_PER_PAGE);

			if (request.isXHR())
				ajaxResponseRenderer.addRender("botonZone", botonZone.getBody()).addRender("eventZone",eventZone.getBody());
		}
		
		void onActionFromAtras(){
			this.startIndex = startIndex-ACCOUNTS_PER_PAGE;
			this.eventBlock = eventService.findEventbyKeywords(keywords, category, admin, startIndex, ACCOUNTS_PER_PAGE);
			
			if (request.isXHR())
				ajaxResponseRenderer.addRender("botonZone", botonZone.getBody()).addRender("eventZone",eventZone.getBody());
		}
		
		Object[] onPassivate() {
			return new Object[] {admin, category, keywords, startIndex};
		}
		
		void onActivate(boolean admin, Long category, String keywords, int startIndex) {
			this.category=category;
			this.keywords=keywords;
			this.admin=admin;
			this.startIndex = startIndex;
			eventBlock = eventService.findEventbyKeywords(keywords, category, admin, startIndex, ACCOUNTS_PER_PAGE);
		}
}
