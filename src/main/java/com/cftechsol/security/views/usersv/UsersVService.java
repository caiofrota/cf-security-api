package com.cftechsol.security.views.usersv;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cftechsol.data.services.GenericService;

/**
 * Users view service.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class UsersVService extends GenericService<UsersVRepository, UsersV, String> {

	public List<UsersV> findByUsername(String username) {
		return this.repository.findByUsername(username);
	}

}
