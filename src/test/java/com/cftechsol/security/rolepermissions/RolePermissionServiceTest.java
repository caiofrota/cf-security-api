package com.cftechsol.security.rolepermissions;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.junit4.SpringRunner;

import com.cftechsol.security.permissions.Permission;
import com.cftechsol.security.permissions.PermissionService;
import com.cftechsol.security.roles.Role;
import com.cftechsol.security.roles.RoleService;

/**
 * Role service test class.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RolePermissionServiceTest {

	@Autowired
	private RolePermissionService service;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PermissionService permissionService;

	@Test(expected = AccessDeniedException.class)
	public void cantUpdateSuperadmin() throws Exception {
		Role role = new Role("ROLEPERMISSION_CANT_UPDATE_SUPERADMIN", null, null);
		Permission permission = new Permission("ROLEPERMISSION_CANT_UPDATE_SUPERADMIN", null);
		permissionService.save(permission);
		roleService.save(role);
		RolePermission example = new RolePermission(role, permission);
		example.setSuperadmin(true);
		example = service.save(example);
		Assert.assertNull(service.save(example));
	}

	@Test(expected = AccessDeniedException.class)
	public void cantDeleteSuperadmin() throws Exception {
		Role role = new Role("ROLEPERMISSION_CANT_DELETE_SUPERADMIN", null, null);
		Permission permission = new Permission("ROLEPERMISSION_CANT_DELETE_SUPERADMIN", null);
		permissionService.save(permission);
		roleService.save(role);
		RolePermission example = new RolePermission(role, permission);
		example.setSuperadmin(true);
		example = service.save(example);
		service.delete(example.getId());
	}

}
