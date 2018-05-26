package com.cftechsol.security.rolepermissions;

import javax.validation.ConstraintViolationException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cftechsol.security.permissions.Permission;
import com.cftechsol.security.roles.Role;

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

	@Test(expected = ConstraintViolationException.class)
	public void PKShouldBeUnique() throws Exception {
		RolePermission example = new RolePermission(new Role("TEST_ROLE_PERMISSION", null, null),
				new Permission("TEST_ROLE_PERMISSION", null));
		example.getRole().setId(2l);
		service.save(example);
		service.save(example);
	}

	@Test(expected = ConstraintViolationException.class)
	public void PKShouldBeUniqueOnSaveAudit() throws Exception {
		RolePermission example = new RolePermission(new Role("TEST_ROLE_PERMISSION_AUDIT", null, null),
				new Permission("TEST_ROLE_PERMISSION_AUDIT", null));
		service.save(example, 1l);
		service.save(example, 1l);
	}

	@Test
	public void cantUpdateSuperadmin() throws Exception {
		RolePermission example = new RolePermission(new Role("SUPERADMIN", null, null),
				new Permission("SUPERADMIN", null));
		example.getRole().setId(1l);
		Assert.assertNull(service.save(example));
	}

	@Test
	public void cantDeleteSuperadmin() throws Exception {
		RolePermission example = new RolePermission(new Role("TEST_ROLE_PERMISSION_AUDIT", null, null),
				new Permission("TEST_ROLE_PERMISSION_AUDIT", null));
		example.getRole().setId(1l);
		service.delete(example.getId());
	}

}
