package com.cftechsol.security.users;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * User repository.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("select u from User u where u.superadmin <> 1")
	List<User> findAll();
	
	@Query("select u from User u")
	List<User> findAllWithSuperadmin();
	
	@Query("select u from User u where u.id = ?1 and u.superadmin <> 1")
	Optional<User> findById(Long id);
	
	@Query("select u from User u where u.id = ?1")
	Optional<User> findByIdWithSuperadmin(Long id);

	@Query("select u from User u where u.email = ?1 and u.superadmin <> 1")
	User findByEmail(String email);
	
	@Query("select u from User u where u.email = ?1")
	User findByEmailWithSuperadmin(String email);
	
	User findByEmailAndPassword(String email, String password);

}
