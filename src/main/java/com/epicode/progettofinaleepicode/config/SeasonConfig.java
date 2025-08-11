package com.epicode.progettofinaleepicode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.epicode.progettofinaleepicode.entity.Partite;
import com.epicode.progettofinaleepicode.entity.PartiteDto;
import com.epicode.progettofinaleepicode.entity.Season;
import com.epicode.progettofinaleepicode.entity.SeasonDto;


@Configuration
public class SeasonConfig {


	@Bean
	@Scope("prototype")
	public Season newSeason() {
		Season  season =new Season();
		
		return  season;
	}
	
	@Bean
	@Scope("prototype")
	public SeasonDto newSeasonDto() {
		SeasonDto  partita = new SeasonDto();
		
		return  partita;
	}

}