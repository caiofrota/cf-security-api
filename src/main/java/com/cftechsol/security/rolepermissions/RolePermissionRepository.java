package com.cftechsol.security.rolepermissions;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Role permission repository.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionPK> {

	@Query("select rp from RolePermission rp where rp.superadmin <> 1")
	List<RolePermission> findAll();

	@Query("select rp from RolePermission rp")
	List<RolePermission> findAllWithSuperadmin();

	@Query("select rp from RolePermission rp where rp.id = ?1 and rp.superadmin <> 1")
	Optional<RolePermission> findById(RolePermissionPK id);

	@Query("select rp from RolePermission rp where rp.id = ?1")
	Optional<RolePermission> findByIdWithSuperadmin(RolePermissionPK id);

	@Query("select rp from RolePermission rp where rp.role.id = ?1 and rp.superadmin <> 1")
	List<RolePermission> findByRoleId(Long id);
	
	@Query("select rp from RolePermission rp where rp.role.id = ?1")
	List<RolePermission> findByRoleIdWithSuperadmin(Long id);

}
