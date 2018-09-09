package com.cftechsol.security.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cftechsol.data.services.GenericService;
import com.cftechsol.rest.exceptions.NonUniqueException;

/**
 * User service.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class UserService extends GenericService<UserRepository, User, Long> {

	@Autowired
	@Lazy
	private PasswordEncoder passwordEncoder;

	private User prepare(User object) throws Exception {
		User user = super.repository.findByEmail(object.getEmail());
		if (user != null && user.getId() != object.getId()) {
			throw new NonUniqueException(object.getClass().getSimpleName(), new String[] { "email" },
					new String[] { object.getEmail() });
		}
		if (object.getId() == null && object.getPassword() != null) {
			object.setPassword(passwordEncoder.encode(object.getPassword()));
		} else if (object.getId() != null) {
			User oldUser = this.findById(object.getId());
			object.setPassword(oldUser.getPassword());
		}
		return object;
	}

	public List<User> findAllWithSuperadmin() {
		return super.repository.findAllWithSuperadmin();
	}

	public User findByIdWithSuperadmin(Long id) {
		return super.repository.findByIdWithSuperadmin(id).get();
	}

	public User findByEmail(String email) {
		return super.repository.findByEmail(email);
	}

	public User findByEmailWithSuperadmin(String email) {
		return super.repository.findByEmailWithSuperadmin(email);
	}

	@Override
	public User save(User object) throws Exception {
		if (object != null && object.getId() != null) {
			User user = this.findByIdWithSuperadmin(object.getId());
			if (user.getSuperadmin()) {
				throw new AccessDeniedException("Forbidden");
			}
		}
		object = prepare(object);
		return super.save(object);
	}

	@Override
	public User save(User object, long id) throws Exception {
		if (object != null && object.getId() != null) {
			User user = this.findByIdWithSuperadmin(object.getId());
			if (user.getSuperadmin()) {
				throw new AccessDeniedException("Forbidden");
			}
		}
		object = prepare(object);
		return super.save(object, id);
	}

	@Override
	public void delete(Long object) throws Exception {
		if (object != null) {
			User user = this.findByIdWithSuperadmin(object);
			if (user != null && user.getSuperadmin()) {
				throw new AccessDeniedException("Forbidden");
			}
		}
		super.delete(object);
	}

	public User changePassword(Long id, String password, Long userId) throws Exception {
		if (password != null) {
			User user = this.findById(id);
			if (user != null) {
				user.setPassword(passwordEncoder.encode(password));
				return super.save(user, userId);
			}
		}
		return null;
	}

}
