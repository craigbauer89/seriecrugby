package com.epicode.progettofinaleepicode.entity;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChannelDto {
	
	private String country;
	private String name;
	private boolean free;

}
