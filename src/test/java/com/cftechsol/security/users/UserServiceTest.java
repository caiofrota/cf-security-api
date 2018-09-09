package com.cftechsol.security.users;

import javax.validation.ConstraintViolationException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.junit4.SpringRunner;

import com.cftechsol.rest.exceptions.NonUniqueException;

/**
 * User service test class.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
	
	@Autowired
	private UserService service;
	
	@Test
	public void shouldSaveWithPasswordEncripted() throws Exception {
		User example = new User("shouldSavePasswordEncripted@company.com", "Password", "User Name", true, null, null);
		example = service.save(example);
		Assert.assertNotEquals(example.getPassword(), "Password");
	}
	
	@Test
	public void shouldSaveAuditWithPasswordEncripted() throws Exception {
		User example = new User("shouldSaveAuditWithPasswordEncripted@company.com", "Password", "User Name", true, null, null);
		example = service.save(example, 2l);
		Assert.assertNotEquals(example.getPassword(), "Password");
	}
	
	@Test
	public void shouldChangePassword() throws Exception {
		User example = service.findById(1l);
		example = service.changePassword(example, "newPassword", example.getId());
		Assert.assertTrue(service.checkPassword(example, "newPassword"));
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void emailShouldntBeNull() throws Exception {
		User example = new User(null, "Password", "User Name", true, null, null);
		service.save(example);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void passwordShouldntBeNull() throws Exception {
		User example = new User("emailShouldntBeNull@company.com", null, "User Name", true, null, null);
		service.save(example);
	}

	@Test(expected = ConstraintViolationException.class)
	public void nameShouldntBeNull() throws Exception {
		User example = new User("nameShouldntBeNull@company.com", "Password", null, true, null, null);
		service.save(example);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void emailShouldBeValid() throws Exception {
		User example = new User("emailShouldBeValid", "Password", "User Name", true, null, null);
		service.save(example);
	}
	
	@Test(expected = NonUniqueException.class)
	public void emailShouldBeUnique() throws Exception {
		User example = new User("emailShouldBeUnique@company.com", "Password", "User Name", true, null, null);
		service.save(example);
		example.setId(null);
		service.save(example);
	}

	@Test(expected = AccessDeniedException.class)
	public void cantUpdateSuperadmin() throws Exception {
		User example = new User("cantUpdateSuperadminUser@company.com", "Password", "User Name", true, null, null);
		example.setSuperadmin(true);
		this.service.save(example);
		Assert.assertNull(service.save(example));
	}

	@Test(expected = AccessDeniedException.class)
	public void cantDeleteSuperadmin() throws Exception {
		User object = new User("cantDeleteSuperadminUser@company.com", "Password", "User Name", true, null, null);
		object.setSuperadmin(true);
		object = this.service.save(object);
		service.delete(object.getId());
	}

}
