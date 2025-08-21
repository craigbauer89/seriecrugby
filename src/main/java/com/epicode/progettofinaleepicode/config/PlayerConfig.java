package com.epicode.progettofinaleepicode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.epicode.progettofinaleepicode.entity.News;
import com.epicode.progettofinaleepicode.entity.NewsDto;
import com.epicode.progettofinaleepicode.entity.Player;
import com.epicode.progettofinaleepicode.entity.PlayerDTO;

@Configuration
public class PlayerConfig {
	
	@Bean
	@Scope("prototype")
	public Player newPlayer() {
		Player  player =new Player();
		
		return  player;
	}
	
	@Bean
	@Scope("prototype")
	public PlayerDTO newPlayerDTO() {
		PlayerDTO  playerDto = new PlayerDTO();
		
		return  playerDto;
		
	}

}
