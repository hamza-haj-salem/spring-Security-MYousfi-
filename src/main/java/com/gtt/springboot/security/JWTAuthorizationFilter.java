package com.gtt.springboot.security;

import java.io.IOException;

import javax.management.RuntimeErrorException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;
											// c est un filter qui va intervenir pour chaque requete
											//chaque requete qui arrive on doit la verifier si elle contient ce token
public class JWTAuthorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {
		String jwt = request.getHeader("Authorization");
		if(jwt == null) {
			throw new RuntimeException("Not Authorized");
		}
		filterChain.doFilter(request, response);
														
														
	}

	

}
