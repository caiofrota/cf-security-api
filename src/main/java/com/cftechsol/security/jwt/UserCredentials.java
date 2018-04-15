package com.cftechsol.security.jwt;

import lombok.Getter;
import lombok.Setter;

/**
 * User credentials pojo.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@Setter
public class UserCredentials {

	private String username;
	private String password;

}
