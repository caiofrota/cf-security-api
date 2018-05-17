package com.cftechsol.security.userroles;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cftechsol.security.controllers.GenericSecuredController;

/**
 * User role controller.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/admin/userroles", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRoleController extends GenericSecuredController<UserRoleService, UserRole, UserRolePK> {

	public UserRoleController() {
		super("USER_ROLES");
	}
	
	@GetMapping(path = "/user/{id}")
	public List<UserRole> findById(@PathVariable Long id) throws Exception {
		return service.findByUserId(id);
	}

}
