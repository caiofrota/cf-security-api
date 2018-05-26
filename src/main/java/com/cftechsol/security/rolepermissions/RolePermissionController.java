package com.cftechsol.security.rolepermissions;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@GetMapping(path = "/role/{id}")
	public List<RolePermission> findById(@PathVariable Long id) throws Exception {
		return service.findByRoleId(id);
	}

}
