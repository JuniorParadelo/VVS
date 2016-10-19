package es.udc.pojoapp.web.pages.user;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojoapp.model.userprofile.UserProfile;
import es.udc.pojoapp.model.userservice.UserProfileDetails;
import es.udc.pojoapp.model.userservice.UserService;
import es.udc.pojoapp.web.pages.Index;
import es.udc.pojoapp.web.pages.create.Bets;
import es.udc.pojoapp.web.services.AuthenticationPolicy;
import es.udc.pojoapp.web.services.AuthenticationPolicyType;
import es.udc.pojoapp.web.util.UserSession;
import es.udc.pojo.modelutil.exceptions.DuplicateInstanceException;

@AuthenticationPolicy(AuthenticationPolicyType.NON_AUTHENTICATED_USERS)
public class Register {

    @Property
    private String loginName;

    @Property
    private String password;

    @Property
    private String retypePassword;

    @Property
    private String firstName;

    @Property
    private String lastName;

    @Property
    private String email;

    @SessionState(create=false)
    private UserSession userSession;
    
    @SessionState(create=false)
   	private Long optionId;

    @Inject
    private UserService userService;

    @Component
    private Form registrationForm;

    @Component(id = "loginName")
    private TextField loginNameField;

    @Component(id = "password")
    private PasswordField passwordField;

    @Inject
    private Messages messages;
    
    @InjectPage
   	private Bets apostar;

    private Long userProfileId;

    void onValidateFromRegistrationForm() {

        if (!registrationForm.isValid()) {
            return;
        }

        if (!password.equals(retypePassword)) {
            registrationForm.recordError(passwordField, messages
                    .get("error-passwordsDontMatch"));
        } else {

            try {
                UserProfile userProfile = userService.registerUser(loginName, password,
                    new UserProfileDetails(firstName, lastName, email));
                userProfileId = userProfile.getUserProfileId();
            } catch (DuplicateInstanceException e) {
                registrationForm.recordError(loginNameField, messages
                        .get("error-loginNameAlreadyExists"));
            }

        }

    }

    Object onSuccess() {
    	userSession = new UserSession();
        userSession.setUserProfileId(userProfileId);
        userSession.setFirstName(firstName);
        userSession.setAdmin(loginName.equals("admin"));
        
        if (optionId == null){
        	return Index.class;
        } else {
        	apostar.setOptionId(optionId);
        	optionId = null;
        	return apostar;
        }

    }

}
