package com.cftechsol.security.permissions;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.cftechsol.data.services.GenericService;
import com.cftechsol.rest.exceptions.NonUniqueException;

/**
 * Permission service.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class PermissionService extends GenericService<PermissionRepository, Permission, Long> {

	private Permission prepare(Permission object) throws Exception {
		if (this.repository.findByCod(object.getCod()) != null) {
			throw new NonUniqueException(object.getClass().getSimpleName(), new String[] { "cod" },
					new String[] { object.getCod() });
		}
		return object;
	}

	public List<Permission> findAllWithSuperadmin() {
		return super.repository.findAllWithSuperadmin();
	}

	public Permission findByIdWithSuperadmin(Long id) {
		return super.repository.findByIdWithSuperadmin(id).get();
	}

	public Permission findByCod(String email) {
		return super.repository.findByCod(email);
	}

	public Permission findByCodWithSuperadmin(String email) {
		return super.repository.findByCodWithSuperadmin(email);
	}

	public Permission save(Permission object) throws Exception {
		if (object != null && object.getId() != null) {
			Permission user = this.findByIdWithSuperadmin(object.getId());
			if (user.getSuperadmin()) {
				throw new AccessDeniedException("Forbidden");
			}
		}
		object = prepare(object);
		return super.save(object);
	}

	public Permission save(Permission object, long id) throws Exception {
		if (object != null && object.getId() != null) {
			Permission user = this.findByIdWithSuperadmin(object.getId());
			if (user.getSuperadmin()) {
				throw new AccessDeniedException("Forbidden");
			}
		}
		object = prepare(object);
		return super.save(object, id);
	}

	@Override
	public void delete(Long object) throws Exception {
		if (object != null) {
			Permission user = this.findByIdWithSuperadmin(object);
			if (user != null && user.getSuperadmin()) {
				throw new AccessDeniedException("Forbidden");
			}
		}
		super.delete(object);
	}

}
