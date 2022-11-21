package com.epicode.progettofinaleepicode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.epicode.progettofinaleepicode.entity.Squadre;
import com.epicode.progettofinaleepicode.entity.SquadreDto;


@Configuration
public class SquadreConfig {


	@Bean
	@Scope("prototype")
	public Squadre newSquadre() {
		Squadre  squadra =new Squadre();
		
		return  squadra;
	}
	
	@Bean
	@Scope("prototype")
	public SquadreDto newSquadreDto() {
		SquadreDto  squadra = new SquadreDto();
		
		return  squadra;
	}

}
