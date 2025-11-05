package com.epicode.progettofinaleepicode.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Participation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Squadre squadra;

	@ManyToOne
	@JoinColumn(name = "classifica_id")
	private Classifica classifica;
	
	
	
	

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
	
	  public void calcolaDifferenza() {
	        this.differenza = this.puntiFatti - this.puntiSubiti;
	    }
	  
	  @PrePersist
	    @PreUpdate
	    private void aggiornaDifferenzaPrimaSalvataggio() {
	        calcolaDifferenza(); // Calcola la differenza automaticamente prima del salvataggio o aggiornamento
	    }
	
//	public Participation(Squadre squadra ) {
//		this.squadra = squadra;
//		}

}
