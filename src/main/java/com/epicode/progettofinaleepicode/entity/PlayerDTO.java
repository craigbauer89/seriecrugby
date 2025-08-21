package com.epicode.progettofinaleepicode.entity;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDTO {
	
	private String name;
	private int tries;

}
