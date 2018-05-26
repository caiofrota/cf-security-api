package com.cftechsol.security.roles;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Role repository.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	@Query("select r from Role r where r.superadmin <> 1")
	List<Role> findAll();
	
	@Query("select r from Role r")
	List<Role> findAllWithSuperadmin();
	
	@Query("select r from Role r where r.id = ?1 and r.superadmin <> 1")
	Optional<Role> findById(Long id);
	
	@Query("select r from Role r where r.id = ?1")
	Optional<Role> findByIdWithSuperadmin(Long id);

	@Query("select r from Role r where r.cod = ?1 and r.superadmin <> 1")
	Role findByCod(String cod);
	
	@Query("select r from Role r where r.cod = ?1")
	Role findByCodWithSuperadmin(String cod);

}
