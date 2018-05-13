package com.cftechsol.security.tokens;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cftechsol.security.jwt.services.TokenAuthenticationService;
import com.cftechsol.security.users.User;
import com.cftechsol.security.users.UserService;

/**
 * Token controller.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/token", produces = MediaType.APPLICATION_JSON_VALUE)
public class TokenController {
	
	@Autowired
	protected TokenService tokenService;
	
	@Autowired
	protected UserService userService;
	
	@PostMapping(path = "/refresh")
	public void tokenRefresh(@RequestBody Token token, HttpServletResponse response) throws IOException {
		try {
			Token found = this.tokenService.findByTokenRefresh(token.getTokenRefresh());
			if (found != null) {
				User user = this.userService.findById(found.getUserId());
				this.tokenService.delete(found);
				TokenAuthenticationService.addAuthentication(response, user.getEmail());
			} else {
				response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
			}
		} catch(Exception e) {
			response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
		}
	}
	
}
