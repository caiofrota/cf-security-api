package com.cftechsol.security.users;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cftechsol.security.controllers.GenericSecuredController;

/**
 * User controller.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/admin/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController extends GenericSecuredController<UserService, User, Long> {

	public UserController() {
		super("USER", "ADMIN");
	}

	@Override
	public User findById(@PathVariable Long id) throws Exception {
		if (id > 1) {
			return super.findById(id);
		}
		return null;
	}

}
