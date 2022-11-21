package com.epicode.progettofinaleepicode.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
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
public class Partite {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDate date;
	
	@OneToOne //(cascade = CascadeType.ALL)
	private Squadre squadra1;
	
	@OneToOne //(cascade = CascadeType.ALL)
	private Squadre squadra2;
	
	private int puntisquadra1 =0;
	private int puntisquadra2  =0;
	private int meteSquadra1 =0;
	private int meteSquadra2 =0;
	private int girone;
	

}
