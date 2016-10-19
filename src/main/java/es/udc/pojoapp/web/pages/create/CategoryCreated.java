package es.udc.pojoapp.web.pages.create;

import es.udc.pojoapp.web.services.AuthenticationPolicy;
import es.udc.pojoapp.web.services.AuthenticationPolicyType;

@AuthenticationPolicy(AuthenticationPolicyType.ADMIN_USERS)
public class CategoryCreated {

	private Long categoryId;
	
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	Long onPassivate() {
		return categoryId;
	}
	
	void onActivate(Long categoryId) {
		this.categoryId = categoryId;
	}
	

}
