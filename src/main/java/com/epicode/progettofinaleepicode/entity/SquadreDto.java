package com.epicode.progettofinaleepicode.entity;



import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SquadreDto {
	
	private String allenatore;
	private String sito;
	private String indirizzo;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private String nome;
	private String telefono;
	private int punti;
	private int vittorie;
	private int pareggi;
	private int sconfitte;
	private int giocate;
	private int meteFatti;
	private int meteSubiti;
	private int puntiSubiti;
	private int puntiFatti;
	private int differenza;
	private Jersey jersey;
	private int girone;

}
