package com.cftechsol.security.jwt;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.cftechsol.security.jwt.services.TokenAuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;

/**
 * JWT authentication filter.
 * 
 * @author Caio Frota {@literal <contact@cftechsol.com>}
 * @version 1.0.0
 * @since 1.0.0
 */
public class JWTAuthenticationFilter extends GenericFilterBean {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		try {
			Authentication authentication = TokenAuthenticationService.getAuthentication((HttpServletRequest) request);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			filterChain.doFilter(request, response);
		} catch (ExpiredJwtException e) {
			// ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, e.getMessage());
			HttpServletResponse res = (HttpServletResponse) response;
			PrintWriter out = res.getWriter();
			res.setStatus(Response.SC_UNAUTHORIZED);
			res.setContentType("application/json");
			out.print(new ObjectMapper().writeValueAsString(""));// apiError));
			out.flush();
		} catch (Exception e) {
			// ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,
			// e.getMessage());
			HttpServletResponse res = (HttpServletResponse) response;
			PrintWriter out = res.getWriter();
			res.setStatus(Response.SC_INTERNAL_SERVER_ERROR);
			res.setContentType("application/json");
			out.print(new ObjectMapper().writeValueAsString(""));// apiError));
			out.flush();
		}
	}

}
