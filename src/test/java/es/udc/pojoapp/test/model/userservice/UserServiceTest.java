package es.udc.pojoapp.test.model.userservice;

import static es.udc.pojoapp.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.udc.pojoapp.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import es.udc.pojo.modelutil.exceptions.DuplicateInstanceException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.pojoapp.model.userprofile.UserProfile;
import es.udc.pojoapp.model.userservice.IncorrectPasswordException;
import es.udc.pojoapp.model.userservice.UserProfileDetails;
import es.udc.pojoapp.model.userservice.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class UserServiceTest {

	private final long NON_EXISTENT_USER_PROFILE_ID = -1;

	@Autowired
	private UserService userService;

	// PR-UN-001
	@Test
	public void testRegisterUserAndFindUserProfile() throws DuplicateInstanceException, InstanceNotFoundException {

		/* Register user and find profile. */
		UserProfile userProfile = userService.registerUser("user", "userPassword",
				new UserProfileDetails("name", "lastName", "user@udc.es"));

		UserProfile userProfile2 = userService.findUserProfile(userProfile.getUserProfileId());

		/* Check data. */
		assertEquals(userProfile, userProfile2);

	}

	// PR-UN-002
	@Test(expected = DuplicateInstanceException.class)
	public void testRegisterDuplicatedUser() throws DuplicateInstanceException, InstanceNotFoundException {

		String loginName = "user";
		String clearPassword = "userPassword";
		UserProfileDetails userProfileDetails = new UserProfileDetails("name", "lastName", "user@udc.es");

		userService.registerUser(loginName, clearPassword, userProfileDetails);

		userService.registerUser(loginName, clearPassword, userProfileDetails);

	}

	// PR-UN-003
	@Test
	public void testLoginClearPassword() throws IncorrectPasswordException, InstanceNotFoundException {

		String clearPassword = "userPassword";
		UserProfile userProfile = registerUser("user", clearPassword);

		UserProfile userProfile2 = userService.login(userProfile.getLoginName(), clearPassword, false);

		assertEquals(userProfile, userProfile2);

	}

	// PR-UN-004
	@Test
	public void testLoginEncryptedPassword() throws IncorrectPasswordException, InstanceNotFoundException {

		UserProfile userProfile = registerUser("user", "clearPassword");

		UserProfile userProfile2 = userService.login(userProfile.getLoginName(), userProfile.getEncryptedPassword(),
				true);

		assertEquals(userProfile, userProfile2);

	}

	// PR-UN-005
	@Test(expected = IncorrectPasswordException.class)
	public void testLoginIncorrectPasword() throws IncorrectPasswordException, InstanceNotFoundException {

		String clearPassword = "userPassword";
		UserProfile userProfile = registerUser("user", clearPassword);

		userService.login(userProfile.getLoginName(), 'X' + clearPassword, false);

	}

	// PR-UN-006
	@Test(expected = IncorrectPasswordException.class)
	public void testLoginIncorrectPasword2() throws IncorrectPasswordException, InstanceNotFoundException {

		String clearPassword = "userPassword";
		UserProfile userProfile = registerUser("user", clearPassword);

		userService.login(userProfile.getLoginName(), 'X' + clearPassword, true);

	}

	// PR-UN-007
	@Test(expected = InstanceNotFoundException.class)
	public void testLoginWithNonExistentUser() throws IncorrectPasswordException, InstanceNotFoundException {

		userService.login("user", "userPassword", false);

	}

	// PR-UN-008
	@Test(expected = InstanceNotFoundException.class)
	public void testFindNonExistentUser() throws InstanceNotFoundException {

		userService.findUserProfile(NON_EXISTENT_USER_PROFILE_ID);

	}

	// PR-UN-009
	@Test
	public void testUpdate() throws InstanceNotFoundException, IncorrectPasswordException {

		/* Update profile. */
		String clearPassword = "userPassword";
		UserProfile userProfile = registerUser("user", clearPassword);

		UserProfileDetails newUserProfileDetails = new UserProfileDetails('X' + userProfile.getFirstName(),
				'X' + userProfile.getLastName(), 'X' + userProfile.getEmail());

		userService.updateUserProfileDetails(userProfile.getUserProfileId(), newUserProfileDetails);

		/* Check changes. */
		userService.login(userProfile.getLoginName(), clearPassword, false);
		UserProfile userProfile2 = userService.findUserProfile(userProfile.getUserProfileId());

		assertEquals(newUserProfileDetails.getFirstName(), userProfile2.getFirstName());
		assertEquals(newUserProfileDetails.getLastName(), userProfile2.getLastName());
		assertEquals(newUserProfileDetails.getEmail(), userProfile2.getEmail());

	}

	// PR-UN-010
	@Test(expected = InstanceNotFoundException.class)
	public void testUpdateWithNonExistentUser() throws InstanceNotFoundException {

		userService.updateUserProfileDetails(NON_EXISTENT_USER_PROFILE_ID,
				new UserProfileDetails("name", "lastName", "user@udc.es"));

	}

	// PR-UN-011
	@Test
	public void testChangePassword() throws InstanceNotFoundException, IncorrectPasswordException {

		/* Change password. */
		String clearPassword = "userPassword";
		UserProfile userProfile = registerUser("user", clearPassword);
		String newClearPassword = 'X' + clearPassword;

		userService.changePassword(userProfile.getUserProfileId(), clearPassword, newClearPassword);

		/* Check new password. */
		userService.login(userProfile.getLoginName(), newClearPassword, false);

	}

	// PR-UN-012
	@Test(expected = IncorrectPasswordException.class)
	public void testChangePasswordWithIncorrectPassword() throws InstanceNotFoundException, IncorrectPasswordException {

		String clearPassword = "userPassword";
		UserProfile userProfile = registerUser("user", clearPassword);

		userService.changePassword(userProfile.getUserProfileId(), 'X' + clearPassword, 'Y' + clearPassword);

	}

	// PR-UN-013
	@Test(expected = InstanceNotFoundException.class)
	public void testChangePasswordWithNonExistentUser() throws InstanceNotFoundException, IncorrectPasswordException {

		userService.changePassword(NON_EXISTENT_USER_PROFILE_ID, "userPassword", "XuserPassword");

	}

	private UserProfile registerUser(String loginName, String clearPassword) {

		UserProfileDetails userProfileDetails = new UserProfileDetails("name", "lastName", "user@udc.es");

		try {

			return userService.registerUser(loginName, clearPassword, userProfileDetails);

		} catch (DuplicateInstanceException e) {
			throw new RuntimeException(e);
		}

	}

}
