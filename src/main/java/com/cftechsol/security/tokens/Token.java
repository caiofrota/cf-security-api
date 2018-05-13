package com.cftechsol.security.tokens;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.cftechsol.security.users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Token entity.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "cf_tokens", uniqueConstraints = @UniqueConstraint(columnNames = "tokenRefresh", name = "cf_tokens_u1"))
public class Token {

	@Id
	private String token;

	@Column(name = "user_id")
	@JsonIgnore
	private Long userId;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("userId")
	@JoinColumn(foreignKey = @ForeignKey(name = "cf_tokens_fk1"))
	@JsonIgnore
	private User user;

	@Column
	@NotNull
	private String tokenRefresh;
	
	@Column(name = "created_on", insertable = false, updatable = false)
	private Date createdOn;
	
	public Token(String token, User user, String tokenRefresh) {
		setToken(token);
		setUser(user);
		setTokenRefresh(tokenRefresh);
	}
	
	protected Long getUserId() {
		return userId;
	}
	
	protected void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public void setUser(User user) {
		if (user != null) {
			setUserId(user.getId());
		}
	}

}
