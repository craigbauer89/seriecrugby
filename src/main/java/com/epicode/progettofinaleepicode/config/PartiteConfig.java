package com.epicode.progettofinaleepicode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.epicode.progettofinaleepicode.entity.Partite;
import com.epicode.progettofinaleepicode.entity.PartiteDto;



@Configuration
public class PartiteConfig {


	@Bean
	@Scope("prototype")
	public Partite newPartite() {
		Partite  partita =new Partite();
		
		return  partita;
	}
	
	@Bean
	@Scope("prototype")
	public PartiteDto newPartiteDto() {
		PartiteDto  partita = new PartiteDto();
		
		return  partita;
	}

}