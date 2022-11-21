package com.epicode.progettofinaleepicode.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Squadre {
	
	
	public Squadre(Long long1) {
		// TODO Auto-generated constructor stub
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	private Jersey jersey;
	
	private String allenatore;
	private String sito;
	private String indirizzo;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private String nome;
	private String telefono;
	private int punti  =0;
	private int vittorie =0;
	private int pareggi =0;
	private int sconfitte =0;
	private int giocate =0;
	private int meteFatti =0;
	private int meteSubiti =0;
	private int puntiSubiti =0;
	private int puntiFatti =0;
	private int differenza =0;
	private int girone;
	
	

}

