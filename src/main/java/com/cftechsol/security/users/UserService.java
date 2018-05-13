package com.cftechsol.security.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cftechsol.rest.exceptions.NonUniqueException;
import com.cftechsol.security.services.SecurityService;

/**
 * User service.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class UserService extends SecurityService<UserRepository, User, Long> {

	@Autowired
	@Lazy
	private PasswordEncoder passwordEncoder;

	@Override
	public List<User> findAll() {
		return this.repository.findByIdGreaterThan(1l);
	}

	private User prepare(User object) throws Exception {
		User user = this.repository.findByEmail(object.getEmail());
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

	public User findByEmail(String email) {
		return this.repository.findByEmail(email);
	}

	/**
	 * Save an user.
	 * 
	 * @param object
	 *            User to save.
	 * @return Object saved.
	 * @throws Exception
	 */
	public User save(User object) throws Exception {
		object = prepare(object);
		return super.save(object);
	}

	/**
	 * Save an user.
	 * 
	 * @param object
	 *            User to save.
	 * @return Object saved.
	 * @throws Exception
	 */
	public User save(User object, long id) throws Exception {
		object = prepare(object);
		return super.save(object, id);
	}

}
