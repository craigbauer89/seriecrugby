package com.epicode.progettofinaleepicode.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationDto {

	
	private int giocate =0;
	private int vittorie =0;
	private int pareggi =0;
	private int sconfitte =0;
	private int meteFatti =0;
	private int meteSubiti =0;
	private int puntiSubiti =0;
	private int puntiFatti =0;
	private int differenza =0;
	private int puntiBonus  =0;
	private int punti  =0;
}
