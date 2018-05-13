package com.cftechsol.security.views.userauthorities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * User authorites view primary key.
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
public class UserAuthoritiesVPK implements Serializable {
	
	private static final long serialVersionUID = -5016396800667731762L;

	@Column(name = "username")
	private String username;
	
	@Column(name = "authorities")
	private String authorities;
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass()) 
            return false;
 
        UserAuthoritiesVPK that = (UserAuthoritiesVPK) o;
        return Objects.equals(username, that.username) && 
               Objects.equals(authorities, that.authorities);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(username, authorities);
    }
	
}
