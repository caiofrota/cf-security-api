package com.cftechsol.security.rolepermissions;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cftechsol.security.controllers.GenericSecuredController;

/**
 * Role permission controller.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/admin/rolepermissions", produces = MediaType.APPLICATION_JSON_VALUE)
public class RolePermissionController extends GenericSecuredController<RolePermissionService, RolePermission, RolePermissionPK> {

	public RolePermissionController() {
		super("ROLE_PERMISSIONS");
	}

}
