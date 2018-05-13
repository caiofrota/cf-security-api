package com.cftechsol.security.rolepermissions;

import org.springframework.stereotype.Service;

import com.cftechsol.security.services.SecurityService;

/**
 * Role permission service.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class RolePermissionService extends SecurityService<RolePermissionRepository, RolePermission, RolePermissionPK> {

}
