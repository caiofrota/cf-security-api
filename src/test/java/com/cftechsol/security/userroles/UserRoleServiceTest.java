package com.cftechsol.security.userroles;

import javax.validation.ConstraintViolationException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cftechsol.security.roles.Role;
import com.cftechsol.security.users.User;

/**
 * Role service test class.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRoleServiceTest {

	@Autowired
	private UserRoleService service;
	
	@Test(expected = ConstraintViolationException.class)
	public void PKShouldBeUnique() throws Exception {
		UserRole example = new UserRole(new User("userRoleService@company.com", "", "", true, null, null), new Role("TEST_ROLE_PERMISSION", null, null));
		service.save(example);
		service.save(example);
	}

	@Test(expected = ConstraintViolationException.class)
	public void PKShouldBeUniqueOnSaveAudit() throws Exception {
		UserRole example = new UserRole(new User("userRoleServiceAudit@company.com", "", "", true, null, null), new Role("TEST_ROLE_PERMISSION_AUDIT", null, null));
		service.save(example, 1l);
		service.save(example, 1l);
	}

	@Test
	public void cantUpdateSuperadmin() throws Exception {
		UserRole example = new UserRole(new User("superadmin@company.com", "", "", true, null, null), new Role("SUPERADMIN", null, null));
		example.getUser().setId(1l);
		Assert.assertNull(service.save(example));
	}

	@Test
	public void cantDeleteSuperadmin() throws Exception {
		UserRole object = new UserRole(new User("userRoleService@company.com", "", "", true, null, null), new Role("TEST_ROLE_PERMISSION", null, null));
		object.getUser().setId(1l);
		service.delete(object.getId());
	}

}
