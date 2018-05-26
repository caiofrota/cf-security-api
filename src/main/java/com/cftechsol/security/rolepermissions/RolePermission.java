package com.cftechsol.security.rolepermissions;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.cftechsol.data.entities.GenericAuditEntity;
import com.cftechsol.security.permissions.Permission;
import com.cftechsol.security.roles.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Role permission entity.
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
@Table(name = "cf_role_permissions")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class RolePermission extends GenericAuditEntity<RolePermissionPK> {

	private static final long serialVersionUID = 2267456055927509651L;

	@EmbeddedId
	private RolePermissionPK id;

	@Column(insertable = false, updatable = false)
	private boolean superadmin;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@MapsId("roleId")
	@JoinColumn(foreignKey = @ForeignKey(name = "cf_role_permissions_fk1"))
	@JsonBackReference(value = "role")
	private Role role;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@MapsId("permissionId")
	@JoinColumn(foreignKey = @ForeignKey(name = "cf_role_permissions_fk2"))
	@JsonBackReference(value = "permission")
	private Permission permission;

	public RolePermission(Role role, Permission permission) {
		this.role = role;
		this.permission = permission;
		this.id = new RolePermissionPK(role.getId(), permission.getId());
	}
	
	public void setId(RolePermissionPK id) {
		this.id = id;
		this.setRole(new Role());
		this.setPermission(new Permission());
		this.getRole().setId(id.getRoleId());
		this.getPermission().setId(id.getPermissionId());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		RolePermission that = (RolePermission) o;
		return Objects.equals(getId(), that.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

}
