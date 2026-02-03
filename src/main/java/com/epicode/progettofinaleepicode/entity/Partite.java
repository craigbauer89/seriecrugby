package com.epicode.progettofinaleepicode.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
	
	@ManyToOne
    @JoinColumn(name = "squadraHome_id", nullable = false)
    private Squadre squadra1;  // First team in the game

    @ManyToOne
    @JoinColumn(name = "squadraAway_id", nullable = false)
    private Squadre squadra2;  
	
	private Long squadra1_id;
	private Long squadra2_id;
	private int puntisquadra1 =0;
	private int puntisquadra2  =0;
	private int meteSquadra1 =0;
	private int meteSquadra2 =0;
	private Long classifica_id;
	private String tickets; 
	private Boolean played;
	private String time;
	
	@ManyToOne
	@JoinColumn(name = "stadium_id")
	private Stadium stadium;
	
	
	@ManyToOne
	@JoinColumn(name = "channel_id")
	private Channel channel;
	
	
	@ManyToOne
	@JoinColumn(name = "mainClassifica_id")
	private Classifica classifica;
	
	public Long getSquadra1_id() {
	    return squadra1 != null ? squadra1.getId() : null;
	}

	public void setSquadra1(Squadre squadra1) {
	    this.squadra1 = squadra1;
	    this.squadra1_id = squadra1 != null ? squadra1.getId() : null;
	}

	public Long getSquadra2_id() {
	    return squadra2 != null ? squadra2.getId() : null;
	}

	public void setSquadra2(Squadre squadra2) {
	    this.squadra2 = squadra2;
	    this.squadra2_id = squadra2 != null ? squadra2.getId() : null;
	}
	
	public Long getClassifica_id() {
	    return classifica != null ? classifica.getId() : this.classifica_id;
	}

	public void setClassifica(Classifica classifica) {
	    this.classifica = classifica;
	    this.classifica_id = classifica != null ? classifica.getId() : null;
	}
	
	

}
