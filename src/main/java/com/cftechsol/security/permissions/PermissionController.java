package com.cftechsol.security.permissions;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cftechsol.security.controllers.GenericSecuredController;

/**
 * Permission controller.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/admin/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissionController extends GenericSecuredController<PermissionService, Permission, Long> {

	public PermissionController() {
		super("PERMISSIONS", "ADMIN");
	}

	@Override
	public Permission findById(@PathVariable Long id) throws Exception {
		if (id > 1) {
			return super.findById(id);
		}
		return null;
	}

}
