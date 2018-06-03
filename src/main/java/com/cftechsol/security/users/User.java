package com.cftechsol.security.users;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.cftechsol.data.entities.GenericAuditEntity;
import com.cftechsol.security.tokens.Token;
import com.cftechsol.security.userroles.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * User entity.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "cf_users", uniqueConstraints = @UniqueConstraint(columnNames = "email", name = "cf_users_u1"))
public class User extends GenericAuditEntity<Long> {

	private static final long serialVersionUID = 4218492105335669330L;

	@Column
	@NotNull
	@Email
	private String email;

	@Column
	@NotNull
	private String password;

	@Column
	@NotNull
	private String name;

	@Column
	@NotNull
	private boolean enabled;

	@Column(updatable = false)
	private Boolean superadmin = false;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonManagedReference(value = "user")
	private List<UserRole> roles = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Token> tokens = new ArrayList<>();

	@JsonIgnore
	public String getPassword() {
		return this.password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}
	
	public User(String email, String password, String name, boolean enabled, List<UserRole> roles, List<Token> tokens) {
		this.setEmail(email);
		this.setPassword(password);
		this.setName(name);
		this.setEnabled(enabled);
		this.setRoles(roles);
		this.setTokens(tokens);
	}
	
	public User(String email, String password, String name, boolean enabled) {
		this(email, password, name, enabled, null, null);
	}

}
