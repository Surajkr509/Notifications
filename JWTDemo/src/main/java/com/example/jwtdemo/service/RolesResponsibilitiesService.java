package com.example.jwtdemo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jwtdemo.model.Role;
import com.example.jwtdemo.repository.RoleRepository;

@Service
public class RolesResponsibilitiesService {
	
	@Autowired
	RoleRepository roleRepository;
	
	
	public Role getRoleById(Long id) {
		Role role=new Role();
		Optional<Role> data=roleRepository.findById(id);
		if(data.isPresent())
			role=data.get();
		return role;
	}

}
