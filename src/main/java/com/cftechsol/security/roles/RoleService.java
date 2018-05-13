package com.cftechsol.security.roles;

import org.springframework.stereotype.Service;

import com.cftechsol.rest.exceptions.NonUniqueException;
import com.cftechsol.security.services.SecurityService;

/**
 * Role service.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class RoleService extends SecurityService<RoleRepository, Role, Long> {

	private Role prepare(Role object) throws Exception {
		if (this.repository.findByCod(object.getCod()) != null) {
			throw new NonUniqueException(object.getClass().getSimpleName(), new String[] { "cod" },
					new String[] { object.getCod() });
		}
		return object;
	}

	/**
	 * Save a role.
	 * 
	 * @param object
	 *            Role to save.
	 * @return Object saved.
	 * @throws Exception
	 */
	public Role save(Role object) throws Exception {
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
	public Role save(Role object, long id) throws Exception {
		object = prepare(object);
		return super.save(object, id);
	}

}
