package com.telusko.config;

import java.beans.Customizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		//disabling csrf token
	    // http.csrf(customizer->customizer.disable());
		//authorizing the request
		//http.authorizeHttpRequests(request -> request.anyRequest().authenticated() );
		//enable the form login
		//http.formLogin(org.springframework.security.config.Customizer.withDefaults());
		//enable for http basics
		//http.httpBasic(org.springframework.security.config.Customizer.withDefaults());
		//make session stateless
		//http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		//return http.build();
		
		 // Configure security settings
		return http.csrf(customizer->customizer.disable()) // Disable CSRF for simplicity
				  .authorizeHttpRequests(request -> request
			      .requestMatchers("register","login") //except these resources apply security for remaining
			      .permitAll()
				  .anyRequest()
				  .authenticated()) // Secure all other endpoints
				  .httpBasic(org.springframework.security.config.Customizer.withDefaults())// Enable basic authentication
				  .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))// Use stateless sessions
				  .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)// Add JWT filter
	              .build();
				  
	}
	
  /*@Bean
	UserDetailsService userDetailsService() {
		UserDetails user1 = User  
				   .withDefaultPasswordEncoder()
				   .username("sai")
				   .password("sai123")
				   .roles("dev")
				   .build();
		
		UserDetails user2 = User  
				   .withDefaultPasswordEncoder()
				   .username("ram")
				   .password("ram123")
				   .roles("dev")
				   .build();
		
		return new InMemoryUserDetailsManager(user1, user2);
	}*/
	
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	    //whatever the password is received that is bcrypted
	    provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
	    provider.setUserDetailsService(userDetailsService); // Set UserDetailsService
	    return provider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();// Return the authentication manager
		
	}

}
