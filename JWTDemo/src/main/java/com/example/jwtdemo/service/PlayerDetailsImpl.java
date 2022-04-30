package com.example.jwtdemo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.jwtdemo.model.Players;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class PlayerDetailsImpl implements UserDetails {

	public static final long serialVersionUID = 1L;

	private Long id;
	private String userName;
	private String email;
	@JsonIgnore
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	private String mobNo;

	private PlayerDetailsImpl(Long id, String userName, String email, String password,
			Collection<? extends GrantedAuthority> authorities, String mobNo) {
		super();
		this.id = id;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
		this.mobNo = mobNo;
	}

	public static PlayerDetailsImpl buildUserWithAuth(Players user) {
		List<GrantedAuthority> authorities= new ArrayList<GrantedAuthority>();
		return new PlayerDetailsImpl(user.getId(), user.getUserName(), user.getEmail(),
				user.getPassword(), authorities, user.getMobNo());
	}

	public Long getId() {
		return id;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	public String getEmail() {
		return email;
	}
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public String getMobNo() {
		return mobNo;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		PlayerDetailsImpl user = (PlayerDetailsImpl) o;
		return Objects.equals(id, user.id);
	}
	
	
}
