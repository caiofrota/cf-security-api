package com.cftechsol.security.permissions;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Permission repository.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

	@Query("select p from Permission p where p.superadmin <> 1")
	List<Permission> findAll();
	
	@Query("select p from Permission p")
	List<Permission> findAllWithSuperadmin();
	
	@Query("select p from Permission p where p.id = ?1 and p.superadmin <> 1")
	Optional<Permission> findById(Long id);
	
	@Query("select p from Permission p where p.id = ?1")
	Optional<Permission> findByIdWithSuperadmin(Long id);

	@Query("select p from Permission p where p.cod = ?1 and p.superadmin <> 1")
	Permission findByCod(String cod);
	
	@Query("select p from Permission p where p.cod = ?1")
	Permission findByCodWithSuperadmin(String cod);

}
