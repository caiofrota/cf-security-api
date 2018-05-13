package com.cftechsol.security.users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * User repository.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
	
	List<User> findByIdGreaterThan(Long id);
	
	@Modifying
	@Query("update User u set u.email = ?1, u.name = ?2 where u.id = ?3")
	@Transactional
	int setFixedEmailAndNameFor(String email, String name, Long id);
	
	@Modifying
	@Query("update User u set u.email = ?1, u.name = ?2, u.updatedBy = ?3, u.updatedOn = now() where u.id = ?4")
	@Transactional
	int setFixedEmailAndNameFor(String email, String name, Long auditId, Long id);

}
