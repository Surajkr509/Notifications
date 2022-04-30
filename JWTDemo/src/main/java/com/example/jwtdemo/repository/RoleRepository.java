package com.example.jwtdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jwtdemo.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Role findByRole(String role);

}
