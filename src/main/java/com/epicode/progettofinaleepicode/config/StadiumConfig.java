package com.epicode.progettofinaleepicode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.epicode.progettofinaleepicode.entity.Player;
import com.epicode.progettofinaleepicode.entity.PlayerDTO;
import com.epicode.progettofinaleepicode.entity.Stadium;
import com.epicode.progettofinaleepicode.entity.StadiumDto;

@Configuration
public class StadiumConfig {
	
	@Bean
	@Scope("prototype")
	public Stadium newStadium() {
		Stadium  stadium =new Stadium();
		
		return  stadium;
	}
	
	@Bean
	@Scope("prototype")
	public StadiumDto newStadiumDto() {
		StadiumDto  stadiumDto = new StadiumDto();
		
		return  stadiumDto;
		
	}

}
