package com.cftechsol.security.roles;

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
public class RoleServiceTest {

	@Autowired
	private RoleService service;

	@Test(expected = ConstraintViolationException.class)
	public void codShouldntBeNull() throws Exception {
		Role example = new Role(null, null, null);
		service.save(example);
	}

	@Test(expected = NonUniqueException.class)
	public void codShouldBeUnique() throws Exception {
		Role example = new Role("ROLE_UNIQUE", null, null);
		service.save(example);
		service.save(example);
	}

	@Test(expected = NonUniqueException.class)
	public void codShouldBeUniqueOnSaveAudit() throws Exception {
		Role example = new Role("ROLE_UNIQUE_AUDIT", null, null);
		service.save(example, 1l);
		service.save(example, 1l);
	}

	@Test(expected = AccessDeniedException.class)
	public void cantUpdateSuperadmin() throws Exception {
		Role example = new Role("ROLE_CANT_UPDATE_SUPERADMIN", null, null);
		example.setSuperadmin(true);
		this.service.save(example);
		Assert.assertNull(service.save(example));
	}

	@Test(expected = AccessDeniedException.class)
	public void cantDeleteSuperadmin() throws Exception {
		Role object = new Role("ROLE_CANT_DELETE_SUPERADMIN", null, null);
		object.setSuperadmin(true);
		object = this.service.save(object);
		service.delete(object.getId());
	}

}
