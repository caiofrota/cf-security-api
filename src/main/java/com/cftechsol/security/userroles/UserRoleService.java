package com.cftechsol.security.userroles;

import org.springframework.stereotype.Service;

import com.cftechsol.security.services.SecurityService;

/**
 * User role service.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class UserRoleService extends SecurityService<UserRoleRepository, UserRole, UserRolePK> {

}
