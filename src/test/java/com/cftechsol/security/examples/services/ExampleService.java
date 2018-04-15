package com.cftechsol.security.examples.services;

import org.springframework.stereotype.Service;

import com.cftechsol.data.services.GenericService;
import com.cftechsol.security.examples.entities.ExampleUser;
import com.cftechsol.security.examples.repositories.ExampleUsersRepository;

/**
 * Example service to execute test.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class ExampleService extends GenericService<ExampleUsersRepository, ExampleUser, Long> {
	
}
