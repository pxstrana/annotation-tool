package com.annotation.config;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * JWT filter to authenticate the user
 * @author Luis Pastrana
 *
 */
public class JWTAuthorizationFilter extends OncePerRequestFilter {

	private final String HEADER = "Authorization";
	private final String PREFIX = "Bearer ";
	private final String SECRET = "mySecretKey";
	
	/**
	 * userDetailsService Service to manage user details in authentication
	 */
	private UserDetailsService userDetailsService;

	/**
	 * Constructor of the JWT filter
	 * @param userDetailsService the userDetailsService we will use
	 */
	public JWTAuthorizationFilter(UserDetailsService userDetailsService) {
		this.userDetailsService=userDetailsService;
	}
	

	/**
	 * Filter before the controllers to authenticate users by token in the RESTful API
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		try {
			if (existsJWTToken(request, response)) {
				Claims claims = validateToken(request);
				if (claims.get("authorities") != null) {
					
					UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(auth);
					
				} else {
					SecurityContextHolder.clearContext();
				}
			}
			chain.doFilter(request, response);
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			return;
		}
	}	

	/**
	 * Checks if the token is valid
	 * @param request the request with the token
	 * @return
	 */
	private Claims validateToken(HttpServletRequest request) {
		String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
		return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
	}

	
	/**
	 * Checks if the token exists
	 * 
	 * @param request the request to check if it has the token
	 * @param res the response
	 * @return
	 */
	private boolean existsJWTToken(HttpServletRequest request, HttpServletResponse res) {
		String authenticationHeader = request.getHeader(HEADER);
		if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX))
			return false;
		return true;
	}

}