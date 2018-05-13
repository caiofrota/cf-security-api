package com.cftechsol.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cftechsol.security.jwt.UserCredentials;
import com.cftechsol.security.users.User;
import com.cftechsol.security.users.UserRepository;
import com.cftechsol.security.views.userauthorities.UserAuthoritiesV;
import com.cftechsol.security.views.userauthorities.UserAuthoritiesVRepository;
import com.cftechsol.security.views.usersv.UsersV;
import com.cftechsol.security.views.usersv.UsersVRepository;
import com.google.gson.Gson;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Authentication test class.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UsersVRepository usersvRepository;

	@Autowired
	private UserAuthoritiesVRepository userAuthoritiesVRepository;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value("${cf.rest.jwt.expirationTime}")
	private long expirationTime;
	@Value("${cf.rest.jwt.secret}")
	private String secret;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		Collection<Filter> filterCollection = webApplicationContext.getBeansOfType(Filter.class).values();
		Filter[] filters = filterCollection.toArray(new Filter[filterCollection.size()]);
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(SecurityMockMvcConfigurers.springSecurity()).addFilters(filters).build();
	}

	@Test
	public void requestToUnsecuredMethodShouldGetOk() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/example/test")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void requestToSecuredMethodShouldGetForbidden() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/example/test"))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	public void shouldLogin() throws Exception {
		String password = passwordEncoder.encode("password");
		userRepository.save(new User("userlogin@company.com", password, "User Login", true, null, null));
		usersvRepository.save(new UsersV("userlogin@company.com", password, true));
		userAuthoritiesVRepository.save(new UserAuthoritiesV("userlogin@company.com", "ADMIN"));
		userAuthoritiesVRepository.save(new UserAuthoritiesV("userlogin@company.com", "TEST"));

		Gson gson = new Gson();
		UserCredentials user = new UserCredentials();
		user.setUsername("userlogin@company.com");
		user.setPassword("password");
		// @formatter:off
		mockMvc.perform(MockMvcRequestBuilders.post("/login")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Origin", "*")
						.content(gson.toJson(user)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.header().exists("Authorization"));
		// @formatter:on
	}

	@Test
	public void requestWithValidTokenToSecuredMethodShouldGetOk() throws Exception {
		String password = passwordEncoder.encode("password");
		userRepository.save(new User("requestWithValidTokenToSecuredMethodShouldGetOk@company.com", password,
				"User Login", true, null, null));
		usersvRepository
				.save(new UsersV("requestWithValidTokenToSecuredMethodShouldGetOk@company.com", password, true));
		userAuthoritiesVRepository
				.save(new UserAuthoritiesV("requestWithValidTokenToSecuredMethodShouldGetOk@company.com", "ADMIN"));
		userAuthoritiesVRepository
				.save(new UserAuthoritiesV("requestWithValidTokenToSecuredMethodShouldGetOk@company.com", "TEST"));

		String username = "requestWithValidTokenToSecuredMethodShouldGetOk@company.com";
		// @formatter:off
		String token = Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/users")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Origin", "*")
						.header("Authorization", "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isOk());
		// @formatter:on
	}

	@Test
	public void requestWithValidTokenWithoutUserToSecuredMethodShouldGetForbidden() throws Exception {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(() -> {
			return "ADMIN";
		});
		authorities.add(() -> {
			return "TEST";
		});
		// @formatter:off
		String token = Jwts.builder().claim("authorities", authorities).setSubject(null)
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/test")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Origin", "*")
						.header("Authorization", "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
		// @formatter:on
	}

	@Test
	public void requestWithExpiredTokenToSecuredMethodShouldGetUnauthorized() throws Exception {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(() -> {
			return "ADMIN";
		});
		authorities.add(() -> {
			return "TEST";
		});
		String username = "user@company.com";
		// @formatter:off
		String token = Jwts.builder().claim("authorities", authorities).setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() - 1))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/test")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Origin", "*")
						.header("Authorization", "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isUnauthorized());
		// @formatter:on
	}

	@Test
	public void requestWithInvalidTokenToSecuredMethodShouldGetInternalServerError() throws Exception {
		// @formatter:off
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/test")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Origin", "*")
						.header("Authorization", "Bearer asd"))
				.andExpect(MockMvcResultMatchers.status().isInternalServerError());
		// @formatter:on
	}

}
