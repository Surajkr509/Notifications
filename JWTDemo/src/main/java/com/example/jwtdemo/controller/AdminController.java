package com.example.jwtdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwtdemo.model.Role;
import com.example.jwtdemo.repository.RoleRepository;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	RoleRepository roleRepository;
	
	@PostMapping
	public ResponseEntity<?> addRole(@RequestBody Role role){
		return ResponseEntity.ok(roleRepository.save(role));
	}
	

}
