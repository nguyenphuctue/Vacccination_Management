package com.training.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class EmployeeDetail implements UserDetails {

	private static final long serialVersionUID = 1L;
	private Employee employee;
	private List<GrantedAuthority> authorities;

	public EmployeeDetail(Employee employee, List<GrantedAuthority> authorities) {
		super();
		this.employee = employee;
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return employee.getPassword();
	}

	@Override
	public String getUsername() {
		return employee.getUserName();
	}
	
	public String getEmail() {
		return employee.getEmail();
	}
	
	public String getBase64Img() {
		return employee.getBase64Img();
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

}
