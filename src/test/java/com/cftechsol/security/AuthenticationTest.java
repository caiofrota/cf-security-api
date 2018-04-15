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

import com.cftechsol.security.examples.entities.ExampleAuthorities;
import com.cftechsol.security.examples.entities.ExampleUser;
import com.cftechsol.security.examples.repositories.ExampleAuthoritiesRepository;
import com.cftechsol.security.examples.repositories.ExampleUsersRepository;
import com.cftechsol.security.jwt.UserCredentials;
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
	private ExampleUsersRepository userRepository;

	@Autowired
	private ExampleAuthoritiesRepository authoritiesRepository;

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
		userRepository.save(new ExampleUser("user@company.com", passwordEncoder.encode("password"), true));
		authoritiesRepository.save(new ExampleAuthorities("user@company.com", "ADMIN"));
		authoritiesRepository.save(new ExampleAuthorities("user@company.com", "TEST"));

		Gson gson = new Gson();
		UserCredentials user = new UserCredentials();
		user.setUsername("user@company.com");
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
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(() -> {
			return "ADMIN";
		});
		String username = "user@company.com";
		// @formatter:off
		String token = Jwts.builder().claim("authorities", authorities).setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/example/test")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Origin", "*")
						.header("Authorization", "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isOk());
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/example/allRoles")
					.contentType(MediaType.APPLICATION_JSON)
					.header("Origin", "*")
					.header("Authorization", "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isOk());
		// @formatter:on
	}

	@Test
	public void requestWithRightRoleToRoleBasedMethodShouldGetOk() throws Exception {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(() -> {
			return "TEST_ROLE";
		});
		String username = "user@company.com";
		// @formatter:off
		String token = Jwts.builder().claim("authorities", authorities).setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/example/test")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Origin", "*")
						.header("Authorization", "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isOk());
		// @formatter:on
	}

	@Test
	public void requestWithWrongRoleToRoleBasedMethodShouldGetForbidden() throws Exception {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(() -> {
			return "OTHER_ROLE";
		});
		String username = "user@company.com";
		// @formatter:off
		String token = Jwts.builder().claim("authorities", authorities).setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/example/test")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Origin", "*")
						.header("Authorization", "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
		// @formatter:on
	}

	@Test
	public void requestWithAllRolesToAllRoleBasedMethodShouldGetForbidden() throws Exception {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(() -> {
			return "ONE_ROLE";
		});
		authorities.add(() -> {
			return "TEST_ROLE";
		});
		String username = "user@company.com";
		// @formatter:off
		String token = Jwts.builder().claim("authorities", authorities).setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/example/allRoles")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Origin", "*")
						.header("Authorization", "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isOk());
		// @formatter:on
	}

	@Test
	public void requestWithWrongRolesToAllRoleBasedMethodShouldGetForbidden() throws Exception {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(() -> {
			return "ONE_ROLE";
		});
		authorities.add(() -> {
			return "OTHER_ROLE";
		});
		String username = "user@company.com";
		// @formatter:off
		String token = Jwts.builder().claim("authorities", authorities).setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/example/allRoles")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Origin", "*")
						.header("Authorization", "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
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

	@Test
	public void requestSecuredFindAllWithRoleShouldGetOk() throws Exception {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(() -> {
			return "EXAMPLE_FIND_ALL";
		});
		String username = "user@company.com";
		// @formatter:off
		String token = Jwts.builder().claim("authorities", authorities).setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/example")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Origin", "*")
						.header("Authorization", "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isOk());
		// @formatter:on
	}

	@Test
	public void requestSecuredFindAllWithoutRoleShouldGetForbidden() throws Exception {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(() -> {
			return "EXAMPLE_FIND_ALL_WRONG";
		});
		String username = "user@company.com";
		// @formatter:off
		String token = Jwts.builder().claim("authorities", authorities).setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/example")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Origin", "*")
						.header("Authorization", "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
		// @formatter:on
	}

	@Test
	public void requestSecuredFindByIdWithRoleShouldGetOk() throws Exception {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(() -> {
			return "EXAMPLE_FIND_BY_ID";
		});
		String username = "user@company.com";

		ExampleUser user = new ExampleUser();
		user.setId(1l);
		userRepository.save(user);

		// @formatter:off
		String token = Jwts.builder().claim("authorities", authorities).setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/example/id/1")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Origin", "*")
						.header("Authorization", "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isOk());
		// @formatter:on
	}

	@Test
	public void requestSecuredFindByIdWithoutRoleShouldGetForbidden() throws Exception {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(() -> {
			return "EXAMPLE_FIND_BY_ID_WRONG";
		});
		String username = "user@company.com";

		ExampleUser user = new ExampleUser();
		user.setId(1l);
		userRepository.save(user);

		// @formatter:off
		String token = Jwts.builder().claim("authorities", authorities).setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/example/id/1")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Origin", "*")
						.header("Authorization", "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
		// @formatter:on
	}

	@Test
	public void requestSecuredSaveWithRoleShouldGetOk() throws Exception {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(() -> {
			return "EXAMPLE_SAVE";
		});
		String username = "user@company.com";

		ExampleUser user = new ExampleUser();

		// @formatter:off
		String token = Jwts.builder().claim("authorities", authorities).setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
		mockMvc.perform(MockMvcRequestBuilders.post("/admin/example")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.header("Origin", "*")
						.header("Authorization", "Bearer " + token)
						.content(new Gson().toJson(user)))
				.andExpect(MockMvcResultMatchers.status().isOk());
		// @formatter:on
	}

	@Test
	public void requestSecuredSaveWithoutRoleShouldGetForbidden() throws Exception {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(() -> {
			return "EXAMPLE_SAVE_WRONG";
		});
		String username = "user@company.com";

		ExampleUser user = new ExampleUser();

		// @formatter:off
		String token = Jwts.builder().claim("authorities", authorities).setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
		mockMvc.perform(MockMvcRequestBuilders.post("/admin/example")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.header("Origin", "*")
						.header("Authorization", "Bearer " + token)
						.content(new Gson().toJson(user)))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
		// @formatter:on
	}

	@Test
	public void requestSecuredDeleteWithRoleShouldGetOk() throws Exception {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(() -> {
			return "EXAMPLE_DELETE";
		});
		String username = "user@company.com";

		ExampleUser user = new ExampleUser();
		user.setId(1l);
		userRepository.save(user);

		// @formatter:off
		String token = Jwts.builder().claim("authorities", authorities).setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
		mockMvc.perform(MockMvcRequestBuilders.delete("/admin/example?id=1")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Origin", "*")
						.header("Authorization", "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isOk());
		// @formatter:on
	}

	@Test
	public void requestSecuredDeleteWithoutRoleShouldGetForbidden() throws Exception {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(() -> {
			return "EXAMPLE_DELETE_WRONG";
		});
		String username = "user@company.com";

		ExampleUser user = new ExampleUser();
		user.setId(1l);
		userRepository.save(user);

		// @formatter:off
		String token = Jwts.builder().claim("authorities", authorities).setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/example?id=1")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Origin", "*")
						.header("Authorization", "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
		// @formatter:on
	}

}
