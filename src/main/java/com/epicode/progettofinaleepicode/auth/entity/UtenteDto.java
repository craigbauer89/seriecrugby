package com.epicode.progettofinaleepicode.auth.entity;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UtenteDto {
	
	private String username;
	private String password;
//	private Set<Role> roles = new HashSet<Role>();

}
