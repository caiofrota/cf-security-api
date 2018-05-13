package com.cftechsol.security.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cftechsol.data.entities.GenericEntity;
import com.cftechsol.data.services.GenericService;
import com.cftechsol.security.permissions.Permission;
import com.cftechsol.security.rolepermissions.RolePermission;
import com.cftechsol.security.roles.Role;
import com.cftechsol.security.userroles.UserRole;
import com.cftechsol.security.users.User;

/**
 * Service to be used in CF Security API.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
public class SecurityService<R extends JpaRepository<E, PK>, E, PK> extends GenericService<R, E, PK> {

	@Override
	public E save(E object) throws Exception {
		if (object instanceof GenericEntity) {
			if (object instanceof User) {
				User user = (User) object;
				if (user.getId() != null && user.getId() <= 1) {
					return null;
				}
			} else if (object instanceof UserRole) {
				UserRole userRole = (UserRole) object;
				if (userRole.getUser().getId() != null && userRole.getUser().getId() <= 1) {
					return null;
				}
			} else if (object instanceof Role) {
				Role role = (Role) object;
				if (role.getId() != null && role.getId() <= 1) {
					return null;
				}
			} else if (object instanceof RolePermission) {
				RolePermission rolePermission = (RolePermission) object;
				if (rolePermission.getRole().getId() != null && rolePermission.getRole().getId() <= 1) {
					return null;
				}
			} else if (object instanceof Permission) {
				Permission permission = (Permission) object;
				if (permission.getId() != null && permission.getId() <= 1) {
					return null;
				}
			}
		}
		return super.save(object);
	}

	@Override
	public void delete(E object) throws Exception {
		if (object instanceof GenericEntity) {
			if (object instanceof User) {
				User user = (User) object;
				if (user.getId() != null && user.getId() <= 1) {
					return;
				}
			} else if (object instanceof UserRole) {
				UserRole userRole = (UserRole) object;
				if (userRole.getUser().getId() != null && userRole.getUser().getId() <= 1) {
					return;
				}
			} else if (object instanceof Role) {
				Role role = (Role) object;
				if (role.getId() != null && role.getId() <= 1) {
					return;
				}
			} else if (object instanceof RolePermission) {
				RolePermission rolePermission = (RolePermission) object;
				if (rolePermission.getRole().getId() != null && rolePermission.getRole().getId() <= 1) {
					return;
				}
			} else if (object instanceof Permission) {
				Permission permission = (Permission) object;
				if (permission.getId() != null && permission.getId() <= 1) {
					return;
				}
			}
		}
		super.delete(object);
	}

}
