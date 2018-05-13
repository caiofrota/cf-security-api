package com.cftechsol.security.views.usersv;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Users view view entity.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cf_users_v")
@Immutable
public class UsersV {
	
	@Id
	@Column(name = "username", insertable = false, updatable = false)
	private String username;

	@Column(name = "password", insertable = false, updatable = false)
	private String authorities;
	
	@Column(name = "enabled", insertable = false, updatable = false)
	private boolean enabled;

}