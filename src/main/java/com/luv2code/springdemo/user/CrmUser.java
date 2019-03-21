package com.luv2code.springdemo.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CrmUser {

	@NotNull(message="is required")
	@Size(min=1, message="is required")	
	private String userName;
	
	@NotNull(message="is required")
	@Size(min=6, message="is required")
	private String password;

	@NotNull(message = " is required")
	private String[] roles;
	
	public CrmUser() {
	}

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
