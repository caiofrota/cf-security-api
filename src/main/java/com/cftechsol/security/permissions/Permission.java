package com.cftechsol.security.permissions;

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
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Permission entity.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "cf_permissions", uniqueConstraints = @UniqueConstraint(columnNames = "cod", name = "cf_permissions_u1"))
public class Permission extends GenericAuditEntity<Long> {

	private static final long serialVersionUID = -4546442309709156287L;

	@Column
	@NotNull
	private String cod;
	
	@Column(updatable = false)
	private Boolean superadmin = false;

	@OneToMany(mappedBy = "permission", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference(value = "permission")
	private List<RolePermission> roles = new ArrayList<>();
	
	public Permission(String cod, List<RolePermission> roles) {
		setCod(cod);
		setRoles(roles);
	}
	
	public Permission(String cod) {
		this(cod, null);
	}
	
}
