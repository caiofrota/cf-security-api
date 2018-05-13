package com.cftechsol.security.tokens;

import org.springframework.stereotype.Service;

import com.cftechsol.data.services.GenericService;

/**
 * Token service.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class TokenService extends GenericService<TokenRepository, Token, String> {

	public Token findByToken(String token) {
		return this.repository.findByToken(token);
	};
	
	public Token findByTokenRefresh(String tokenRefresh) {
		return this.repository.findByTokenRefresh(tokenRefresh);
	};

}
