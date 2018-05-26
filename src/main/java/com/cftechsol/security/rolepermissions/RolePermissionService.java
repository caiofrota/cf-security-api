package com.cftechsol.security.rolepermissions;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.cftechsol.data.services.GenericService;

/**
 * Role permission service.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class RolePermissionService extends GenericService<RolePermissionRepository, RolePermission, RolePermissionPK> {

	public List<RolePermission> findAllWithSuperadmin() {
		return super.repository.findAllWithSuperadmin();
	}

	public RolePermission findByIdWithSuperadmin(RolePermissionPK id) {
		Optional<RolePermission> rolePermission = super.repository.findByIdWithSuperadmin(id);
		return (rolePermission.isPresent()) ? rolePermission.get() : null;
	}

	public List<RolePermission> findByRoleId(Long userId) {
		return super.repository.findByRoleId(userId);
	}

	public List<RolePermission> findByRoleIdWithSuperadmin(Long userId) {
		return super.repository.findByRoleIdWithSuperadmin(userId);
	}

	@Override
	public RolePermission save(RolePermission object) throws Exception {
		if (object != null && object.getId() != null) {
			RolePermission rolePermission = this.findByIdWithSuperadmin(object.getId());
			if (rolePermission != null && rolePermission.isSuperadmin()) {
				throw new AccessDeniedException("Forbidden");
			}
		}
		return super.save(object);
	}

	@Override
	public RolePermission save(RolePermission object, long id) throws Exception {
		if (object != null && object.getId() != null) {
			RolePermission rolePermission = this.findByIdWithSuperadmin(object.getId());
			if (rolePermission != null && rolePermission.isSuperadmin()) {
				throw new AccessDeniedException("Forbidden");
			}
		}
		return super.save(object, id);
	}

	@Override
	public void delete(RolePermissionPK object) throws Exception {
		if (object != null) {
			RolePermission user = this.findByIdWithSuperadmin(object);
			if (user != null && user.isSuperadmin()) {
				throw new AccessDeniedException("Forbidden");
			}
		}
		super.delete(object);
	}

}
