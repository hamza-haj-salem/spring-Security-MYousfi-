package com.gtt.springboot.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.management.RuntimeErrorException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

											// c est un filter qui va intervenir pour chaque requete
											//chaque requete qui arrive on doit la verifier si elle contient ce token
public class JWTAuthorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {
		
		
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Origin", "Origin,Accept,X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers-authorization");
		response.addHeader("Access-Control-Expose-Headers","Access-Control-Allow-Origin, Access-Control-Allow-Credentials, authorization");
		if(request.getMethod().equals("OPTIONS")) {
			response.setStatus(HttpServletResponse.SC_OK);
		}
		/*else if(request.getRequestURI().equals("/login")) {
			filterChain.doFilter(request, response);
			return;
		}*/
		else {
		
			String jwtToken = request.getHeader(SecurityParams.JWT_HEADER_NAME);//on suppose que dans notre req il ya un 
			//System.out.println("TOKEN :"+jwtToken);
			if(jwtToken == null || !jwtToken.startsWith(SecurityParams.HEADER_PREFIX)) {
				filterChain.doFilter(request, response); //ON doit passer au prochain filter
				return;
			}
			JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SecurityParams.SECRET)).build();
			String jwt =jwtToken.substring(SecurityParams.HEADER_PREFIX.length());
			DecodedJWT decodedJWT = verifier.verify(jwt);
			//System.out.println("JWT  :"+jwt);
			String username = decodedJWT.getSubject();
			//List<String> roles=new ArrayList<>();
			//if(decodedJWT.getClaims().get("roles") != null)
			List<String> roles = decodedJWT.getClaims().get("role").asList(String.class); /*getClaims().get("roles").asList(String.class);*/
			//System.out.println("USERNAME  :"+username);
			//System.out.println("ROLES  :"+roles);
			Collection<GrantedAuthority> authorities = new ArrayList<>();
			roles.forEach(rn -> {
				authorities.add(new SimpleGrantedAuthority(rn));
			});
			UsernamePasswordAuthenticationToken user =
					new UsernamePasswordAuthenticationToken(username,null,authorities);
			SecurityContextHolder.getContext().setAuthentication(user);
			filterChain.doFilter(request, response);
			
		}
			
		
		
														
														
	}

	

}
