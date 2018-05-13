package com.cftechsol.security.views.userauthorities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * User authorites view entity.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cf_user_authorities_v")
@Immutable
public class UserAuthoritiesV {
	
	@EmbeddedId
	private UserAuthoritiesVPK id;
	
	@Column(name = "username", insertable = false, updatable = false)
	private String username;

	@Column(name = "authorities", insertable = false, updatable = false)
	private String authorities;
	
	public UserAuthoritiesV(String username, String authorities) {
		setUsername(username);
		setAuthorities(authorities);
		setId(new UserAuthoritiesVPK(username, authorities));
	}

}