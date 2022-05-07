package com.example.jwtdemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.jwtdemo.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Role findByRole(String role);

	@Query("SELECT r FROM Role r WHERE r.role != 'ADMIN'")
	List<Role> findAllRole();

}
