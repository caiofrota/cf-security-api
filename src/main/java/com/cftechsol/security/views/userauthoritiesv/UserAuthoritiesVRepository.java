package com.cftechsol.security.views.userauthoritiesv;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User authorities view repository.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Repository
public interface UserAuthoritiesVRepository extends JpaRepository<UserAuthoritiesV, String> {

	List<UserAuthoritiesV> findByUsername(String useranme);

}
