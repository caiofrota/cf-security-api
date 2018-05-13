package com.cftechsol.security.tokens;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Token repository.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Repository
public interface TokenRepository extends JpaRepository<Token, String> {

	Token findByToken(String token);
	
	Token findByTokenRefresh(String tokenRefresh);

}
