package com.cftechsol.security.views.usersv;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Users view repository.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Repository
public interface UsersVRepository extends JpaRepository<UsersV, String> {

	List<UsersV> findByUsername(String useranme);

}
