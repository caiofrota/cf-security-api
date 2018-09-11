package com.cftechsol.security.views.userauthoritiesv;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cftechsol.data.services.GenericService;

/**
 * User authorities view service.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class UserAuthoritiesVService extends GenericService<UserAuthoritiesVRepository, UserAuthoritiesV, String> {

	public List<UserAuthoritiesV> findByUsername(String username) {
		return this.repository.findByUsername(username);
	}

}
