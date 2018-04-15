package com.cftechsol.security.examples.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cftechsol.security.examples.entities.ExampleUser;

/**
 * Example users repository to run the tests.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
public interface ExampleUsersRepository extends JpaRepository<ExampleUser, Long> {

}
