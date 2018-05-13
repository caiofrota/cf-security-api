package com.cftechsol.security.permissions;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cftechsol.rest.exceptions.NonUniqueException;
import com.cftechsol.security.services.SecurityService;

/**
 * Permission service.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class PermissionService extends SecurityService<PermissionRepository, Permission, Long> {

	private Permission prepare(Permission object) throws Exception {
		if (this.repository.findByCod(object.getCod()) != null) {
			throw new NonUniqueException(object.getClass().getSimpleName(), new String[] { "cod" },
					new String[] { object.getCod() });
		}
		return object;
	}

	@Override
	public List<Permission> findAll() {
		return this.repository.findByIdGreaterThan(1l);
	}
	
	/**
	 * Save a role.
	 * 
	 * @param object
	 *            Role to save.
	 * @return Object saved.
	 * @throws Exception
	 */
	public Permission save(Permission object) throws Exception {
		object = prepare(object);
		return super.save(object);
	}

	/**
	 * Save a role.
	 * 
	 * @param object
	 *            Role to save.
	 * @return Object saved.
	 * @throws Exception
	 */
	public Permission save(Permission object, long id) throws Exception {
		object = prepare(object);
		return super.save(object, id);
	}

}
