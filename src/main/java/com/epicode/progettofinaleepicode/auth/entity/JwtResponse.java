package com.epicode.progettofinaleepicode.auth.entity;

import java.util.List;



import lombok.Data;

//@Data
public class JwtResponse {
	
	 private Utente user;
	    private String jwtToken;
	    
	    
//	private String token;
//	private String type = "Bearer";
//	private Long id;
//	private String username;
//	private List<String> roles;
//
//	public JwtResponse(String accessToken, Long id, String username, List<String> roles) {
//		this.token = accessToken;
//		this.id = id;
//		this.username = username;
//		this.roles = roles;
//	}

	 public JwtResponse(Utente user, String jwtToken) {
	        this.user = user;
	        this.jwtToken = jwtToken;
	    }

	    public Utente getUser() {
	        return user;
	    }

	    public void setUser(Utente user) {
	        this.user = user;
	    }

	    public String getJwtToken() {
	        return jwtToken;
	    }

	    public void setJwtToken(String jwtToken) {
	        this.jwtToken = jwtToken;
	    }
	}