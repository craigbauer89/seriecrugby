
package com.epicode.progettofinaleepicode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.epicode.progettofinaleepicode.entity.Channel;
import com.epicode.progettofinaleepicode.entity.ChannelDto;
import com.epicode.progettofinaleepicode.entity.Stadium;
import com.epicode.progettofinaleepicode.entity.StadiumDto;

@Configuration
public class ChannelConfig {
	
	@Bean
	@Scope("prototype")
	public Channel newChannel() {
		Channel  channel =new Channel();
		
		return  channel;
	}
	
	@Bean
	@Scope("prototype")
	public ChannelDto newChannelDto() {
		ChannelDto  channelDto = new ChannelDto();
		
		return  channelDto;
		
	}

}
