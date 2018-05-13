package com.cftechsol.security.roles;

import javax.validation.ConstraintViolationException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
		Role example = new Role("ADMIN", null, null);
		service.save(example);
		service.save(example);
	}

	@Test(expected = NonUniqueException.class)
	public void codShouldBeUniqueOnSaveAudit() throws Exception {
		Role example = new Role("ADMIN_AUDIT", null, null);
		service.save(example, 1l);
		service.save(example, 1l);
	}

	@Test
	public void cantUpdateSuperadmin() throws Exception {
		Role example = new Role("SUPERADMIN", null, null);
		example.setId(1l);
		Assert.assertNull(service.save(example));
	}

	@Test
	public void cantDeleteSuperadmin() throws Exception {
		Role object = new Role("SUPERADMIN", null, null);
		object.setId(1l);
		service.delete(object);
	}

}
