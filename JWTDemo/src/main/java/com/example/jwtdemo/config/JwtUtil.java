package com.example.jwtdemo.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.jwtdemo.model.Players;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	
	@Value("${example.jwtdemo.jwtSecret}")
	private String jwtSecret;
	
	@Value("${example.jwtdemo.jwtExpirationMs}")
	private int jwtExpirationMs;

	public String generateJwtToken(Players user) {
		return Jwts.builder().setSubject((user.getEmail())).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

}
