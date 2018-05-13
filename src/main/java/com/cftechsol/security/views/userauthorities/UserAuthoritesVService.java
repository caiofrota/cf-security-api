package com.cftechsol.security.views.userauthorities;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cftechsol.data.services.GenericService;

/**
 * User authorites view service.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class UserAuthoritesVService extends GenericService<UserAuthoritiesVRepository, UserAuthoritiesV, String> {

	public List<UserAuthoritiesV> findByUsername(String username) {
		return this.repository.findByUsername(username);
	}

}
