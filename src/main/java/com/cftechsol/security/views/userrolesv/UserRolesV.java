package com.cftechsol.security.views.userrolesv;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * User roles view entity.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cf_user_roles_v")
@Immutable
public class UserRolesV {

	@EmbeddedId
	private UserRolesVPK id;

	@Column(name = "username", insertable = false, updatable = false)
	private String username;

	@Column(name = "roles", insertable = false, updatable = false)
	private String roles;

	public UserRolesV(String username, String roles) {
		setUsername(username);
		setRoles(roles);
		setId(new UserRolesVPK(username, roles));
	}

}