package com.epicode.progettofinaleepicode.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.epicode.progettofinaleepicode.auth.entity.Role;
import com.epicode.progettofinaleepicode.auth.entity.Utente;
import com.epicode.progettofinaleepicode.auth.entity.UtenteDto;
import com.epicode.progettofinaleepicode.auth.repository.RoleRepository;
import com.epicode.progettofinaleepicode.entity.Squadre;
import com.epicode.progettofinaleepicode.entity.SquadreDto;




@Configuration
public class UserConfig {
	
//	private RoleRepository  roleRepository;


	@Bean
	@Scope("prototype")
	public Utente newUser() {
		Utente  user =new Utente();
		
		
		return  user;
	}
	
	@Bean
	@Scope("prototype")
	public UtenteDto newUtenteDto() {
		UtenteDto  user = new UtenteDto();
		
		return  user;
	}

}
