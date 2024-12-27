package com.telusko.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class HelloController {

	@GetMapping("/get")
	public String greet(HttpServletRequest request) {
		return "welcome to spring boot tutorial  "+ request.getSession().getId();
	}
}
