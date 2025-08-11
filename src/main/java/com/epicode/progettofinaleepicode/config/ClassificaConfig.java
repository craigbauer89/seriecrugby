package com.epicode.progettofinaleepicode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.epicode.progettofinaleepicode.entity.Championship;
import com.epicode.progettofinaleepicode.entity.ChampionshipDto;
import com.epicode.progettofinaleepicode.entity.Classifica;
import com.epicode.progettofinaleepicode.entity.ClassificaDto;




@Configuration
public class ClassificaConfig {


	@Bean
	@Scope("prototype")
	public Classifica newClassifica() {
		Classifica  classifica =new Classifica();
		
		return  classifica;
	}
	
	@Bean
	@Scope("prototype")
	public ClassificaDto newClassificaDto() {
		ClassificaDto  classificadto = new ClassificaDto();
		
		return  classificadto;
	}

}