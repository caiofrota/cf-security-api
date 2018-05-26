package com.cftechsol.security.roles;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.cftechsol.data.services.GenericService;
import com.cftechsol.rest.exceptions.NonUniqueException;

/**
 * Role service.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class RoleService extends GenericService<RoleRepository, Role, Long> {

	private Role prepare(Role object) throws Exception {
		if (this.repository.findByCod(object.getCod()) != null) {
			throw new NonUniqueException(object.getClass().getSimpleName(), new String[] { "cod" },
					new String[] { object.getCod() });
		}
		return object;
	}

	public List<Role> findAllWithSuperadmin() {
		return super.repository.findAllWithSuperadmin();
	}

	public Role findByIdWithSuperadmin(Long id) {
		return super.repository.findByIdWithSuperadmin(id).get();
	}

	public Role findByCod(String email) {
		return super.repository.findByCod(email);
	}

	public Role findByCodWithSuperadmin(String email) {
		return super.repository.findByCodWithSuperadmin(email);
	}

	public Role save(Role object) throws Exception {
		if (object != null && object.getId() != null) {
			Role user = this.findByIdWithSuperadmin(object.getId());
			if (user.isSuperadmin()) {
				throw new AccessDeniedException("Forbidden");
			}
		}
		object = prepare(object);
		return super.save(object);
	}

	public Role save(Role object, long id) throws Exception {
		object = prepare(object);
		return super.save(object, id);
	}

	@Override
	public void delete(Long object) throws Exception {
		if (object != null) {
			Role user = this.findByIdWithSuperadmin(object);
			if (user != null && user.isSuperadmin()) {
				throw new AccessDeniedException("Forbidden");
			}
		}
		super.delete(object);
	}

}
