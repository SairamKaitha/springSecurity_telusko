package com.telusko.config;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.telusko.service.JwtService;
import com.telusko.service.MyUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private ApplicationContext context;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzZXZlcmVzIiwiaWF0IjoxNzM1MDM1MzgzLCJleHAiOjE3MzUwMzU0OTF9.yTTxYh5lVO6sv19tTERnwxclsc5nUzaUJWZGtxhcNiM
	   // Extract the Authorization header
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;
		
	   // Check if the header contains a Bearer token
		if(authHeader!=null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7); // Extract the token after "Bearer "
			username=jwtService.extractUserName(token); // Extract username from the token
			
		}
		
        // Validate the token and set the user authentication
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
			UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
            if (jwtService.validateToken(token, userDetails)) {
            	
            	// Set the authentication in the security context
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

		 // Continue with the filter chain
        filterChain.doFilter(request, response);
			
		}
			
	}


