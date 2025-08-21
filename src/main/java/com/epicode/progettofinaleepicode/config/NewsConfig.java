package com.epicode.progettofinaleepicode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.epicode.progettofinaleepicode.entity.Classifica;
import com.epicode.progettofinaleepicode.entity.ClassificaDto;
import com.epicode.progettofinaleepicode.entity.News;
import com.epicode.progettofinaleepicode.entity.NewsDto;

@Configuration
public class NewsConfig {
	
	@Bean
	@Scope("prototype")
	public News newNews() {
		News  news =new News();
		
		return  news;
	}
	
	@Bean
	@Scope("prototype")
	public NewsDto newNewsDto() {
		NewsDto  newsdto = new NewsDto();
		
		return  newsdto;
	}

}
