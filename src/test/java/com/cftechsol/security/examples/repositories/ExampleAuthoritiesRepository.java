package com.cftechsol.security.examples.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cftechsol.security.examples.entities.ExampleAuthorities;

/**
 * Example authorities repository to run the tests.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
public interface ExampleAuthoritiesRepository extends JpaRepository<ExampleAuthorities, Long> {

}
