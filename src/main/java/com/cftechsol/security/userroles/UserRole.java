package com.cftechsol.security.userroles;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.cftechsol.data.entities.GenericAuditEntity;
import com.cftechsol.security.roles.Role;
import com.cftechsol.security.users.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * User role entity.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "cf_user_roles")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class UserRole extends GenericAuditEntity<UserRolePK> {
	
	private static final long serialVersionUID = -3247384486820416078L;

	@EmbeddedId
	private UserRolePK id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("userId")
	@JoinColumn(foreignKey = @ForeignKey(name = "cf_user_roles_fk1"))
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("roleId")
	@JoinColumn(foreignKey = @ForeignKey(name = "cf_user_roles_fk2"))
	private Role role;
	
	public UserRole(User user, Role role) {
		setUser(user);
		setRole(role);
		setId(new UserRolePK(user.getId(), role.getId()));
	}
	
}
