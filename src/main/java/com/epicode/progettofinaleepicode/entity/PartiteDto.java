package com.epicode.progettofinaleepicode.entity;



import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartiteDto {
	
	private LocalDate date;
	private Long squadra1_id;
	private Long squadra2_id;
	private int puntisquadra1;
	private int puntisquadra2;
	private int meteSquadra1;
	private int meteSquadra2;
	private Long classifica_id;
	private String tickets; 
	private Boolean played;

}
