package com.cftechsol.security.userroles;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * User role primary key.
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
public class UserRolePK implements Serializable {
	
	private static final long serialVersionUID = -5016396800667731762L;

	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "role_id")
	private Long roleId;
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass()) 
            return false;
 
        UserRolePK that = (UserRolePK) o;
        return Objects.equals(userId, that.userId) && 
               Objects.equals(roleId, that.roleId);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(userId, roleId);
    }
	
}
