package com.cftechsol.security.userroles;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.cftechsol.data.services.GenericService;

/**
 * User role service.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class UserRoleService extends GenericService<UserRoleRepository, UserRole, UserRolePK> {

	public List<UserRole> findAllWithSuperadmin() {
		return super.repository.findAllWithSuperadmin();
	}

	public UserRole findByIdWithSuperadmin(UserRolePK id) {
		Optional<UserRole> userRole = super.repository.findByIdWithSuperadmin(id);
		return (userRole.isPresent()) ? userRole.get() : null;
	}

	public List<UserRole> findByUserId(Long userId) {
		return super.repository.findByUserId(userId);
	}

	public List<UserRole> findByUserIdWithSuperadmin(Long userId) {
		return super.repository.findByUserIdWithSuperadmin(userId);
	}

	@Override
	public UserRole save(UserRole object) throws Exception {
		if (object != null && object.getId() != null) {
			UserRole user = this.findByIdWithSuperadmin(object.getId());
			if (user != null && user.isSuperadmin()) {
				throw new AccessDeniedException("Forbidden");
			}
		}
		return super.save(object);
	}

	@Override
	public UserRole save(UserRole object, long id) throws Exception {
		if (object != null && object.getId() != null) {
			UserRole user = this.findByIdWithSuperadmin(object.getId());
			if (user != null && user.isSuperadmin()) {
				throw new AccessDeniedException("Forbidden");
			}
		}
		return super.save(object, id);
	}

	@Override
	public void delete(UserRolePK object) throws Exception {
		if (object != null) {
			UserRole user = this.findByIdWithSuperadmin(object);
			if (user != null && user.isSuperadmin()) {
				throw new AccessDeniedException("Forbidden");
			}
		}
		super.delete(object);
	}

}
