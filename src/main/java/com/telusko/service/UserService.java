package com.telusko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.telusko.entity.Users;
import com.telusko.repo.UserRepo;

@Service
public class UserService {

	@Autowired
	private UserRepo repo;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtService jwtService;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
	
	public Users register(Users user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return repo.save(user);
	}

	public String verify(Users users) {
	Authentication authentication =	authManager.authenticate(new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword()));
	if(authentication.isAuthenticated())
		return jwtService.generateToken(users.getUsername());
	return "fail";
	}
	
}
