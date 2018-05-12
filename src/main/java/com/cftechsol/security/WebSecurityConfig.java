package com.cftechsol.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cftechsol.security.jwt.JWTAuthenticationFilter;
import com.cftechsol.security.jwt.JWTLoginFilter;

/**
 * Web security configurations.
 * 
 * Properties:
 * 
 * {@literal <propertie> : <default value>}
 * 
 * {@literal @formatter:off}
 * cf.security.query.users_by_username : select username, password, enabled from users where username = ?
 * cf.security.query.authorities_by_username : select username, authority from authorities where username = ?
 * cf.security.path.login : /login
 * cf.security.path.secured : /admin
 * {@literal @formatter:on}
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired @Lazy
	private PasswordEncoder passwordEncoder;

	@Value("${cf.security.query.users_by_username:select username, password, enabled from users where username = ?}")
	private String USER_BY_USERNAME_QUERY;

	@Value("${cf.security.query.authorities_by_username:select username, authority from authorities where username = ?}")
	private String AUTHORITIES_BY_USERNAME_QUERY;

	@Value("${cf.security.path.login:/login}")
	private String LOGIN_PATH;

	@Value("${cf.security.path.secured:/admin}")
	private String SECURED_PATH;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// @formatter:off
		httpSecurity.csrf().disable().authorizeRequests()
				.antMatchers(HttpMethod.POST, LOGIN_PATH).permitAll()
				.antMatchers(SECURED_PATH + "/**").authenticated().and()
				.addFilterBefore(new JWTLoginFilter(LOGIN_PATH, authenticationManager()), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		// @formatter:on
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// @formatter:off
		auth.jdbcAuthentication()
				.dataSource(dataSource)
				.passwordEncoder(passwordEncoder)
				.usersByUsernameQuery(USER_BY_USERNAME_QUERY)
				.authoritiesByUsernameQuery(AUTHORITIES_BY_USERNAME_QUERY);
		// @formatter:on
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
