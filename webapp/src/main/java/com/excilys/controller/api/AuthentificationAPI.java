package com.excilys.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.config.JwtUtil;
import com.excilys.core.AuthentificationRequest;
import com.excilys.core.AuthentificationResponse;
import com.excilys.service.UserDetailsServiceImpl;

@RestController
public class AuthentificationAPI {
	
	private AuthenticationManager authenticationManager;
	private UserDetailsServiceImpl userDetailsServiceImpl;
	private JwtUtil jwtUtil;
	
	

	
	public AuthentificationAPI(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsServiceImpl, JwtUtil jwtUtil) {
		super();
		this.authenticationManager = authenticationManager;
		this.userDetailsServiceImpl = userDetailsServiceImpl;
		this.jwtUtil = jwtUtil;
	}




	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthentificationRequest authentificationRequest) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authentificationRequest.getUsername(), authentificationRequest.getPassword()));
		} catch(BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
		final UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(authentificationRequest.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthentificationResponse(jwt));
	}
	
	
	
}
