package com.gtt.springboot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder bcpe = getBCPE();
		auth.inMemoryAuthentication().withUser("admin").password(bcpe.encode("1234")).roles("ADMIN","USER");
		auth.inMemoryAuthentication().withUser("user1").password(bcpe.encode("1234")).roles("USER");
		//super.configure(auth);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();//Cross-site Request Forgery est une attack ** 
							  //spring Sécurité fournit une protection CSRF par défaut
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);// on dit à
												// spring ce n'est pas oblig d'utiliser les session et 
										//dans ce cas là c'est à nous de gérer les session par jwt
		//http.formLogin();// generer le formulaire de login
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/categories/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/products/**").permitAll();
		http.authorizeRequests().antMatchers("/categories/**").hasAuthority("ADMIN");
		http.authorizeRequests().antMatchers("/products/**").hasAuthority("USER");
		http.authorizeRequests().anyRequest().authenticated();//Tous les restes necessite une auth
		http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);//on va ajouter un filter qui va intercepter les requets
								// before  : filter qui va se placer en 1er plan **
								//il ya plusiers filters qu'on peurt les ajouter 
	}
	
	@Bean
	public BCryptPasswordEncoder getBCPE() {
		return new BCryptPasswordEncoder();
		
	}

}
