package com.cftechsol.security.userroles;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.junit4.SpringRunner;

import com.cftechsol.security.roles.Role;
import com.cftechsol.security.roles.RoleService;
import com.cftechsol.security.users.User;
import com.cftechsol.security.users.UserService;

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
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;
	
	@Test(expected = AccessDeniedException.class)
	public void cantUpdateSuperadmin() throws Exception {
		User user = new User("cantUpdateSuperadmin@company.com", "", "", true, null, null);
		Role role = new Role("USERROLE_CANT_UPDATE_SUPERADMIN", null, null);
		userService.save(user);
		roleService.save(role);
		UserRole example = new UserRole(user, role);
		example.setSuperadmin(true);
		service.save(example);
		Assert.assertNull(service.save(example));
	}

	@Test(expected = AccessDeniedException.class)
	public void cantDeleteSuperadmin() throws Exception {
		User user = new User("cantDeleteSuperadmin@company.com", "", "", true, null, null);
		Role role = new Role("USERROLE_CANT_DELETE_SUPERADMIN", null, null);
		userService.save(user);
		roleService.save(role);
		UserRole example = new UserRole(user, role);
		example.setSuperadmin(true);
		example = service.save(example);
		service.delete(example.getId());
	}

}
