package com.cftechsol.security.tokens;

import java.util.Collection;
import java.util.Date;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cftechsol.security.users.User;
import com.cftechsol.security.users.UserRepository;
import com.cftechsol.security.views.userauthoritiesv.UserAuthoritiesV;
import com.cftechsol.security.views.userauthoritiesv.UserAuthoritiesVRepository;
import com.cftechsol.security.views.usersv.UsersV;
import com.cftechsol.security.views.usersv.UsersVRepository;

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
public class TokenControllerTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UsersVRepository usersvRepository;

	@Autowired
	private UserAuthoritiesVRepository userAuthoritiesVRepository;

	@Autowired
	private TokenRepository tokenRepository;

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
	public void refreshTokenWithValidTokenRefreshShouldGetOk() throws Exception {
		String password = passwordEncoder.encode("password");
		User user = new User("uservalid@company.com", password, "User Login", true, null, null);
		userRepository.save(user);
		usersvRepository.save(new UsersV("uservalid@company.com", password, true));
		userAuthoritiesVRepository.save(new UserAuthoritiesV("uservalid@company.com", "ADMIN"));
		userAuthoritiesVRepository.save(new UserAuthoritiesV("uservalid@company.com", "TEST"));

		String username = "uservalid@company.com";
		// @formatter:off
		String token = Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
		
		String tokenRefresh = Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
		
		tokenRepository.save(new Token(token, user, tokenRefresh));
		
		mockMvc.perform(MockMvcRequestBuilders.post("/token/refresh")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"tokenRefresh\":\"" + tokenRefresh + "\"}")
						.header("Origin", "*"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.header().exists("Authorization"));
		// @formatter:on
	}

	@Test
	public void refreshTokenWithInvalidTokenRefreshShouldGetUnauthorized() throws Exception {
		String password = passwordEncoder.encode("password");
		User user = new User("userinvalid@company.com", password, "User Login", true, null, null);
		userRepository.save(user);
		usersvRepository.save(new UsersV("userinvalid@company.com", password, true));
		userAuthoritiesVRepository.save(new UserAuthoritiesV("userinvalid@company.com", "ADMIN"));
		userAuthoritiesVRepository.save(new UserAuthoritiesV("userinvalid@company.com", "TEST"));

		String username = "userinvalid@company.com";
		// @formatter:off
		String token = Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
		
		String tokenRefresh = Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
		
		tokenRepository.save(new Token(token, user, tokenRefresh));
		
		mockMvc.perform(MockMvcRequestBuilders.post("/token/refresh")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"tokenRefresh\":\"abc\"}")
						.header("Origin", "*"))
				.andExpect(MockMvcResultMatchers.status().isUnauthorized());
		// @formatter:on
	}

}
