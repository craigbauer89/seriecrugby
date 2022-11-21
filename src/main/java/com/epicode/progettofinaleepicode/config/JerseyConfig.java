package com.epicode.progettofinaleepicode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.epicode.progettofinaleepicode.entity.Jersey;
import com.epicode.progettofinaleepicode.entity.JerseyDto;



@Configuration
public class JerseyConfig {


	@Bean
	@Scope("prototype")
	public Jersey newJersey() {
		Jersey  jersey =new Jersey();
		
		return  jersey;
	}
	
	@Bean
	@Scope("prototype")
	public JerseyDto newJerseyDto() {
		JerseyDto  jersey = new JerseyDto();
		
		return  jersey;
	}

}
