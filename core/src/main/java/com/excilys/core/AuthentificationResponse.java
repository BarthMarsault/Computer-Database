package com.excilys.core;

public class AuthentificationResponse {
	private final String jwt;

	public AuthentificationResponse(String jwt) {
		super();
		this.jwt = jwt;
	}

	public String getJwt() {
		return jwt;
	}
	
	
}