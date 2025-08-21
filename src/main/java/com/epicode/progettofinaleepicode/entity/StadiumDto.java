package com.epicode.progettofinaleepicode.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StadiumDto {
	private String indirizzo;
	private String picture;
	private String name;
}
