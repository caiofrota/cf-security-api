package com.cftechsol.security.controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cftechsol.data.services.GenericService;
import com.cftechsol.rest.controllers.GenericController;

public class GenericSecuredController<S extends GenericService<? extends JpaRepository<E, PK>, E, PK>, E, PK> extends GenericController<S, E, PK> {

	private String prefix;
	
	public GenericSecuredController(String prefix) {
		super();
		this.prefix = prefix;
	}
	
	@Override
	public List<E> findAll() throws Exception {
		hasAnyRole(prefix + "_FIND_ALL");
		return super.findAll();
	}
	
	@Override
	public E findById(@PathVariable PK id) throws Exception {
		hasAnyRole(prefix + "_FIND_BY_ID");
		return super.findById(id);
	}
	
	@Override
	public E save(@RequestBody E object) throws Exception {
		hasAnyRole(prefix + "_SAVE");
		return super.save(object);
	}
	
	@Override
	public void delete(@RequestParam PK id) throws Exception {
		hasAnyRole(prefix + "_DELETE");
		super.delete(id);
	}
	
	/**
	 * Check if the authenticated user has one of the roles.
	 * 
	 * @param roles Roles to check.
	 */
	protected void hasAnyRole(String... roles) {
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		if (authorities.contains(new SimpleGrantedAuthority("ADMIN"))) {
			return;
		}
		long count = 0;
		for (String role : roles) {
			count++;
			if (authorities.contains(new SimpleGrantedAuthority(role))) {
				return;
			} else {
				if (count == roles.length) {
					throw new AccessDeniedException("Access is denied");
				}
			}
		}
	}

	/**
	 * Check if the authenticated user has all roles.
	 * 
	 * @param roles Roles to check.
	 */
	protected void hasAllRoles(String... roles) {
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		if (authorities.contains(new SimpleGrantedAuthority("ADMIN"))) {
			return;
		}
		for (String role : roles) {
			if (!authorities.contains(new SimpleGrantedAuthority(role))) {
				throw new AccessDeniedException("Access is denied");
			}
		}
	}
	
}
