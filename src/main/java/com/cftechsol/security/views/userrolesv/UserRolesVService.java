package com.cftechsol.security.views.userrolesv;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cftechsol.data.services.GenericService;

/**
 * User roles view service.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class UserRolesVService extends GenericService<UserRolesVRepository, UserRolesV, String> {

	public List<UserRolesV> findByUsername(String username) {
		return this.repository.findByUsername(username);
	}

}
