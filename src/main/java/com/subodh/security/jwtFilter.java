package com.subodh.security;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class jwtFilter extends OncePerRequestFilter{
	
	private final jwtService jwtservice;
	private final UserDetailservice userDetailservice;
	@Override
	protected void doFilterInternal( @NotNull HttpServletRequest request,
			 @NotNull HttpServletResponse response,
			 @NotNull FilterChain filterChain)
			throws ServletException, IOException {
		if(request.getServletPath().contains("/api/v1/auth")) {
			filterChain.doFilter(request, response);
			return;
		}
		final String authheader = request.getHeader(HttpHeaders.AUTHORIZATION);
		final String jwt;
		final String userEmail;
		
		if(authheader == null || !authheader.startsWith("Bearer")) {
			filterChain.doFilter(request, response);
			return;
		}
		jwt = authheader.substring(7);
		userEmail = jwtservice.extractUsername(jwt);
		if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() ==  null){
			UserDetails userDetails = userDetailservice.loadUserByUsername(userEmail);
			if(jwtservice.isTokenValid(userEmail, userDetails)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
			authToken.setDetails(
					new WebAuthenticationDetailsSource().buildDetails(request));
			
					
			
			}
			filterChain.doFilter(request, response);
		}
		
	}

	
}
