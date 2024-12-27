package com.telusko.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.telusko.entity.Student;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class StudentController {

	private List<Student> students = new ArrayList<>(List.of(
			new Student(1, "sai", 80),
			new Student(2, "ram", 90),
			new Student(3, "shiva", 70)
			));
	
	@GetMapping("/fetch")
	public List<Student> getAllStudent(){
		return students;
	}
	
	@PostMapping("/create")
	public Student creatStudent(@RequestBody Student student) {
		  students.add(student);
		  return student;
	}
	
	@GetMapping("/csrf")
	public CsrfToken getCsrfToken(HttpServletRequest request) {
		return (CsrfToken) request.getAttribute("_csrf");
	}
}
