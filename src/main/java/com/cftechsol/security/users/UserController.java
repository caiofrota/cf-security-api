package com.cftechsol.security.users;

import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cftechsol.rest.exceptions.BadRequestException;
import com.cftechsol.rest.exceptions.CFMessages;
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
		if (object != null && object.getCurrentPassword() != null && object.getNewPassword() != null) {
			String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = this.userService.findByEmailWithSuperadmin(username);
			if (user != null && this.userService.checkPassword(user, object.getCurrentPassword())) {
				return this.userService.changePassword(user, object.getNewPassword(), user.getId());
			} else {
				throw new BadRequestException(CFMessages.CURRENT_PASSWORD_IS_WRONG.getMessage(), CFMessages.CURRENT_PASSWORD_IS_WRONG.getMessageRef());
			}
		} else {
			throw new BadRequestException(CFMessages.MISSING_ARGUMENTS.getMessage(), CFMessages.MISSING_ARGUMENTS.getMessageRef());
		}
	}

}
