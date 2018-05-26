package com.cftechsol.security.roles;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cftechsol.security.controllers.GenericSecuredController;

/**
 * Role controller.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/admin/roles", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoleController extends GenericSecuredController<RoleService, Role, Long> {

	public RoleController() {
		super("ROLES", "ADMIN");
	}

}
