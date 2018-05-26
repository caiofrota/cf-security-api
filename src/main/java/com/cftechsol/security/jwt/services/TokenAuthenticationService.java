package com.cftechsol.security.jwt.services;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import com.cftechsol.security.tokens.Token;
import com.cftechsol.security.tokens.TokenService;
import com.cftechsol.security.users.User;
import com.cftechsol.security.users.UserService;
import com.cftechsol.security.views.userauthorities.UserAuthoritesVService;
import com.cftechsol.security.views.userauthorities.UserAuthoritiesV;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Token authentication service.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class TokenAuthenticationService {

	private static final long DEF_EXPIRATIONTIME = 1000 * 60 * 60; // 1 hour
	private static final String DEF_SECRET = "CFTechSolutions";

	private static final String TOKEN_PREFIX = "Bearer";
	private static final String HEADER_STRING = "Authorization";

	private static long expirationTime;
	private static String secret;

	private static TokenService tokenService;
	private static UserService userService;

	private static UserAuthoritesVService userAuthoritiesVService;

	public static void addAuthentication(HttpServletResponse res, String username) throws IOException {
		try {
			String JWT = Jwts.builder().setSubject(username)
					.setExpiration(new Date(System.currentTimeMillis() + TokenAuthenticationService.expirationTime))
					.signWith(SignatureAlgorithm.HS512, TokenAuthenticationService.secret).compact();

			String JWTRefresh = Jwts.builder().setSubject(username)
					.setExpiration(new Date(System.currentTimeMillis() + (int) (Math.random() * 10000 + 1000)))
					.signWith(SignatureAlgorithm.HS512, TokenAuthenticationService.secret).compact();

			User user = userService.findByEmailWithSuperadmin(username);

			if (user != null) {
				System.out.println(user.getId() + " : " + JWT);
				Token token = new Token(JWT, user, JWTRefresh);
				tokenService.save(token);
				
				String authorities = null;
				for (UserAuthoritiesV userAuthority : userAuthoritiesVService.findByUsername(user.getEmail())) {
					if (authorities == null) {
						authorities = "";
					} else {
						authorities += ",";
					}
					authorities += "\"" + userAuthority.getId().getAuthorities() + "\"";
				}

				String tokenStr = TokenAuthenticationService.TOKEN_PREFIX + " " + token.getToken();
				res.addHeader(TokenAuthenticationService.HEADER_STRING, tokenStr);
				res.setContentType(MediaType.APPLICATION_JSON_VALUE);
				res.getOutputStream().print("{\"token\": \"" + token.getToken() + "\", \"tokenRefresh\": \""
						+ token.getTokenRefresh() + "\", \"permissions\": [" + authorities + "]}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Authentication getByToken(String tokenStr) {
		tokenStr = tokenStr.replace(TokenAuthenticationService.TOKEN_PREFIX, "").trim();

		Claims claims = Jwts.parser().setSigningKey(TokenAuthenticationService.secret).parseClaimsJws(tokenStr)
				.getBody();
		String user = claims.getSubject();

		String authorities = null;
		for (UserAuthoritiesV userAuthority : userAuthoritiesVService.findByUsername(user)) {
			if (authorities == null) {
				authorities = "";
			} else {
				authorities += ",";
			}
			authorities += (String) userAuthority.getId().getAuthorities();
		}
		// @formatter:off
		return user != null
				? new UsernamePasswordAuthenticationToken(user, null,
						AuthorityUtils.commaSeparatedStringToAuthorityList(authorities))
				: null;
		// @formatter:on
	}

	public static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(TokenAuthenticationService.HEADER_STRING);
		if (token != null) {
			return getByToken(token);
		}
		return null;
	}

	@Value("${cf.rest.jwt.expirationTime:" + TokenAuthenticationService.DEF_EXPIRATIONTIME + "}")
	public void setExpirationTime(long expirationTime) {
		TokenAuthenticationService.expirationTime = expirationTime;
	}

	@Value("${cf.rest.jwt.secret:" + TokenAuthenticationService.DEF_SECRET + "}")
	public void setSecret(String secret) {
		TokenAuthenticationService.secret = secret;
	}

	@Autowired
	public void setTokenService(TokenService tokenService) {
		TokenAuthenticationService.tokenService = tokenService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		TokenAuthenticationService.userService = userService;
	}

	@Autowired
	public void setUserAuthoritiesVService(UserAuthoritesVService userAuthoritiesVService) {
		TokenAuthenticationService.userAuthoritiesVService = userAuthoritiesVService;
	}

}
