package com.cftechsol.security.users;

import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cftechsol.security.controllers.GenericSecuredController;
import com.cftechsol.security.users.dto.UserPasswordDTO;

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

	@PostMapping(path = "/changepwd")
	public User changeOwnPassword(@RequestBody UserPasswordDTO object) throws Exception {
		this.roles.add(prefix + "_CHANGEOWNPWD");
		hasAnyRole(roles.stream().toArray(String[]::new));
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = this.userService.findByEmailWithSuperadmin(username);
		return this.service.changePassword(user.getId(), object.getPassword(), user.getId());
	}

}
