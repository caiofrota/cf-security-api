package com.cftechsol.security.userroles;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * User role repository.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRolePK> {

	@Query("select ur from UserRole ur where ur.superadmin <> 1")
	List<UserRole> findAll();
	
	@Query("select ur from UserRole ur")
	List<UserRole> findAllWithSuperadmin();
	
	@Query("select ur from UserRole ur where ur.id = ?1 and ur.superadmin <> 1")
	Optional<UserRole> findById(UserRolePK id);
	
	@Query("select ur from UserRole ur where ur.id = ?1")
	Optional<UserRole> findByIdWithSuperadmin(UserRolePK id);

	@Query("select ur from UserRole ur where ur.user.id = ?1 and ur.superadmin <> 1")
	List<UserRole> findByUserId(Long id);
	
	@Query("select ur from UserRole ur where ur.user.id = ?1")
	List<UserRole> findByUserIdWithSuperadmin(Long id);

}
