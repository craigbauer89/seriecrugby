package com.epicode.progettofinaleepicode.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = {"homeGames", "classifica", "awaygames", "players"})
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler", "homeGames", "awaygames", "classifica", "players"})
public class Squadre {
	
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne 
	@JoinColumn(name="jersey_id")
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
	
	@OneToOne
	@JoinColumn(name = "stadium_id")
	private Stadium stadium;
	
	//@OneToMany(mappedBy = "squadra", fetch = FetchType.EAGER)
	@OneToMany(mappedBy = "squadra")
	private List<Player> players;
	
	//@OneToMany(mappedBy = "squadra1",fetch = FetchType.EAGER)
	@OneToMany(mappedBy = "squadra1")
    private List<Partite> homeGames;  // Games where this team is squadra1

	//@OneToMany(mappedBy = "squadra2",fetch = FetchType.EAGER)
    @OneToMany(mappedBy = "squadra2")
    private List<Partite> awaygames; 
	
	
	//@ManyToMany(mappedBy = "squadre",fetch = FetchType.EAGER)
	@ManyToMany(mappedBy = "squadre")
	private List<Classifica> classifica = new ArrayList<>();
	
	  public void calcolaDifferenza() {
	        this.differenza = this.puntiFatti - this.puntiSubiti;
	    }

	    // Lifecycle hook che si attiva prima di persistere o aggiornare l'entità nel database
	    @PrePersist
	    @PreUpdate
	    private void aggiornaDifferenzaPrimaSalvataggio() {
	        calcolaDifferenza(); // Calcola la differenza automaticamente prima del salvataggio o aggiornamento
	    }

}

