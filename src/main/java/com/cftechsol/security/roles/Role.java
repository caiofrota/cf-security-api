package com.cftechsol.security.roles;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.cftechsol.data.entities.GenericAuditEntity;
import com.cftechsol.security.rolepermissions.RolePermission;
import com.cftechsol.security.userroles.UserRole;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Role entity.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cf_roles", uniqueConstraints = @UniqueConstraint(columnNames = "cod", name = "cf_roles_u1"))
public class Role extends GenericAuditEntity<Long> {

	private static final long serialVersionUID = 8074166695491840656L;

	@Column
	@NotNull
	private String cod;

	@Column(insertable = false, updatable = false)
	private boolean superadmin;

	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference(value = "role")
	private List<RolePermission> permissions = new ArrayList<>();

	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference(value = "role")
	private List<UserRole> users = new ArrayList<>();

	public Role(String cod, List<RolePermission> permissions, List<UserRole> users) {
		setCod(cod);
		setPermissions(permissions);
		setUsers(users);
	}

	public Role(String cod) {
		this(cod, null, null);
	}

}
