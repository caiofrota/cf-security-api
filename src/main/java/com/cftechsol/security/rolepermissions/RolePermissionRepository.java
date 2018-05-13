package com.cftechsol.security.rolepermissions;

import org.springframework.data.jpa.repository.JpaRepository;
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

}
