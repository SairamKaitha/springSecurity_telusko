package com.telusko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.telusko.entity.Users;
import com.telusko.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService service;
	
	@PostMapping("/register")
	public Users register(@RequestBody Users users ) {
		return service.register(users);
		
	}
	
	@PostMapping("/login")
	public String login(@RequestBody Users users) {
		return service.verify(users);
	}
}
