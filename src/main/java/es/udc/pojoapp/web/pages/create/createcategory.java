package es.udc.pojoapp.web.pages.create;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojoapp.model.category.Category;
import es.udc.pojoapp.model.eventservice.EventService;
import es.udc.pojoapp.web.services.AuthenticationPolicy;
import es.udc.pojoapp.web.services.AuthenticationPolicyType;

@AuthenticationPolicy(AuthenticationPolicyType.ADMIN_USERS)
public class createcategory {

		@Property
		private Long userId;
		
		@Property
		private String name;
		
		@Component
		private Form createCategoryForm;	
		
		@Inject
		private Messages messages;
		
		@Inject
		private EventService eventService;
		
		@InjectPage
		private CategoryCreated categoryCreated;
		
		void onValidateFromCreateCategoryForm() {
			
			if (!createCategoryForm.isValid()) {
				return;
			};
			Category category = null;
			try{
				category = eventService.createCategory(name);
			}catch(Exception e){
				createCategoryForm.recordError(messages
	                    .get("error-categoriaDuplicado"));
			}
			categoryCreated.setCategoryId(category.getIdentificador());
		}
			
		
		Object onSuccess() {
			
			return categoryCreated;
			
		}
	

}
