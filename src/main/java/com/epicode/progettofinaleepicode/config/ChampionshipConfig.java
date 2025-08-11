package com.epicode.progettofinaleepicode.config;





import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.epicode.progettofinaleepicode.entity.Championship;
import com.epicode.progettofinaleepicode.entity.ChampionshipDto;
import com.epicode.progettofinaleepicode.entity.Partite;
import com.epicode.progettofinaleepicode.entity.PartiteDto;
import com.epicode.progettofinaleepicode.entity.Season;
import com.epicode.progettofinaleepicode.entity.SeasonDto;


@Configuration
public class ChampionshipConfig {


	@Bean
	@Scope("prototype")
	public Championship newChampionship() {
		Championship  championship =new Championship();
		
		return  championship;
	}
	
	@Bean
	@Scope("prototype")
	public ChampionshipDto newChampionshipDto() {
		ChampionshipDto  championshipdto = new ChampionshipDto();
		
		return  championshipdto;
	}

}