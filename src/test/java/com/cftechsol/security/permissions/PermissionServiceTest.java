package com.cftechsol.security.permissions;

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
 * Role service test class.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PermissionServiceTest {

	@Autowired
	private PermissionService service;

	@Test(expected = ConstraintViolationException.class)
	public void codShouldntBeNull() throws Exception {
		Permission example = new Permission(null, null);
		service.save(example);
	}

	@Test(expected = NonUniqueException.class)
	public void codShouldBeUnique() throws Exception {
		Permission example = new Permission("PERMISSION_UNIQUE", null);
		service.save(example);
		service.save(example);
	}

	@Test(expected = NonUniqueException.class)
	public void codShouldBeUniqueOnSaveAudit() throws Exception {
		Permission example = new Permission("PERMISSION_UNIQUE_AUDIT", null);
		service.save(example, 1l);
		service.save(example, 1l);
	}

	@Test(expected = AccessDeniedException.class)
	public void cantUpdateSuperadmin() throws Exception {
		Permission example = new Permission("PERMISSION_CANT_UPDATE_SUPERADMIN", null);
		example.setSuperadmin(true);
		service.save(example);
		Assert.assertNull(service.save(example));
	}

	@Test(expected = AccessDeniedException.class)
	public void cantDeleteSuperadmin() throws Exception {
		Permission object = new Permission("PERMISSION_CANT_DELETE_SUPERADMIN", null);
		object.setSuperadmin(true);
		object = this.service.save(object);
		service.delete(object.getId());
	}
	
}
