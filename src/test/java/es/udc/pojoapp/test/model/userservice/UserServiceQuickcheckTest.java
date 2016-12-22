package es.udc.pojoapp.test.model.userservice;

import static com.pholser.junit.quickcheck.internal.Ranges.checkRange;
import static es.udc.pojoapp.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.udc.pojoapp.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;

import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.pojoapp.model.userservice.IncorrectPasswordException;
import es.udc.pojoapp.model.userservice.UserProfileDetails;
import es.udc.pojoapp.model.userservice.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class UserServiceQuickcheckTest {

	@Autowired
	private UserService userService;

	private Random delegate = new Random();

	public double nextDouble() {
		return delegate.nextDouble();
	}

	public double nextDouble(double min, double max) {
		int comparison = checkRange(null, min, max);
		return comparison == 0 ? min : min + (max - min) * nextDouble();
	}

	@Test(expected = InstanceNotFoundException.class)
	public void testFindNonExistentUser() throws InstanceNotFoundException {

		long userProfile = (long) nextDouble(-1000, 0);
		userService.findUserProfile(userProfile);

	}

	@Test(expected = InstanceNotFoundException.class)
	public void testUpdateWithNonExistentUser() throws InstanceNotFoundException {

		long userProfile = (long) nextDouble(-1000, 0);
		userService.updateUserProfileDetails(userProfile, new UserProfileDetails("name", "lastName", "user@udc.es"));

	}

	@Test(expected = InstanceNotFoundException.class)
	public void testChangePasswordWithNonExistentUser() throws InstanceNotFoundException, IncorrectPasswordException {

		long userProfile = (long) nextDouble(-1000, 0);
		userService.changePassword(userProfile, "userPassword", "XuserPassword");

	}

	@Test(expected = InstanceNotFoundException.class)
	public void testFindUserNotFound() throws InstanceNotFoundException {

		userService.findUserProfile((long) nextDouble(-100000, 1000000));

	}

}
