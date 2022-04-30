package com.example.jwtdemo.model;

import javax.validation.constraints.NotBlank;

public class JwtRequest {
	
	@NotBlank(message="Please provide email address")
	private String email;
	
	@NotBlank (message="Please provide correct password")
	private String password;
	
	public JwtRequest(String email,String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public JwtRequest() {
	
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	

}
