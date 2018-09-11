package com.cftechsol.security.views.userrolesv;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * User roles view primary key.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserRolesVPK implements Serializable {

	private static final long serialVersionUID = -7332069886864575169L;

	@Column(name = "username")
	private String username;

	@Column(name = "roles")
	private String roles;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		UserRolesVPK that = (UserRolesVPK) o;
		return Objects.equals(username, that.username) && Objects.equals(roles, that.roles);
	}

	@Override
	public int hashCode() {
		return Objects.hash(roles);
	}

}
