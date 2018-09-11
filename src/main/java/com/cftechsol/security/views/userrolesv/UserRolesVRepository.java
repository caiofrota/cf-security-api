package com.cftechsol.security.views.userrolesv;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User roles view repository.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Repository
public interface UserRolesVRepository extends JpaRepository<UserRolesV, String> {

	List<UserRolesV> findByUsername(String useranme);

}
