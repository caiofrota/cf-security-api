package com.cftechsol.security.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.cftechsol.data.services.GenericService;
import com.cftechsol.rest.controllers.GenericController;
import com.cftechsol.security.users.User;
import com.cftechsol.security.users.UserService;

/**
 * Generic Secured Controller with common methods to accelerate the creation of
 * controllers.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 *
 * @param <S>
 *            Service
 * @param <E>
 *            Entity
 * @param <PK>
 *            Primary key
 */
public class GenericSecuredController<S extends GenericService<? extends JpaRepository<E, PK>, E, PK>, E, PK>
		extends GenericController<S, E, PK> {

	protected String prefix;
	protected List<String> roles;
	protected boolean audit = true;
	
	@Autowired
	protected UserService userService;

	public GenericSecuredController(String prefix) {
		super();
		this.prefix = prefix;
		this.roles = new ArrayList<String>();
	}

	public GenericSecuredController(String prefix, String... roles) {
		super();
		this.prefix = prefix;
		this.roles = new ArrayList<String>();
		for (String role : roles) {
			this.roles.add(role);
		}
	}

	@Override
	public List<E> findAll() throws Exception {
		this.roles.add(prefix + "_FIND_ALL");
		hasAnyRole(roles.stream().toArray(String[]::new));
		return super.findAll();
	}

	@Override
	public E findById(@PathVariable PK id) throws Exception {
		this.roles.add(prefix + "_FIND_BY_ID");
		hasAnyRole(roles.stream().toArray(String[]::new));
		return super.findById(id);
	}

	@Override
	public E save(@RequestBody E object) throws Exception {
		this.roles.add(prefix + "_SAVE");
		hasAnyRole(roles.stream().toArray(String[]::new));
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = this.userService.findByEmailWithSuperadmin(username);
		if (audit) {
			return this.service.save(object, user.getId());
		} else {
			return super.save(object);
		}
	}

	@Override
	public void delete(@RequestBody PK object) throws Exception {
		this.roles.add(prefix + "_DELETE");
		hasAnyRole(roles.stream().toArray(String[]::new));
		super.delete(object);
	}

	/**
	 * Check if the authenticated user has one of the roles.
	 * 
	 * @param roles
	 *            Roles to check.
	 */
	protected void hasAnyRole(String... roles) {
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		if (authorities.contains(new SimpleGrantedAuthority("SUPERADMIN"))) {
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
	 * @param roles
	 *            Roles to check.
	 */
	protected void hasAllRoles(String... roles) {
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		if (authorities.contains(new SimpleGrantedAuthority("SUPERADMIN"))) {
			return;
		}
		for (String role : roles) {
			if (!authorities.contains(new SimpleGrantedAuthority(role))) {
				throw new AccessDeniedException("Access is denied");
			}
		}
	}

}
