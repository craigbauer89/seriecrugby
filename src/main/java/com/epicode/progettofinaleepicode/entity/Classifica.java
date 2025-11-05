package com.epicode.progettofinaleepicode.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = {"squadre","partite","participation"})
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler", "partite", "participation" })
public class Classifica {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	
	@ManyToOne
	@JoinColumn(name = "championship_id")
	private Championship championship;
	
	//@ManyToMany(fetch = FetchType.EAGER)
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			  name = "Classifica_Squadra", 
			  joinColumns = @JoinColumn(name = "classifica_id"), 
			  inverseJoinColumns = @JoinColumn(name = "squadre_id"))
	private List<Squadre> squadre = new ArrayList<>();
	
	@OneToMany(mappedBy = "classifica")
	private List<Participation> participation = new ArrayList<>();
	
//	public void aggiungiSquadra(Squadre squadra) {
//		Participation participation = new Participation(squadra);
//		
//		//participation.setClassifica(this);
//	    this.participation.add(participation);
//
//	    if (squadra.getParticipation() == null) {
//	        squadra.setParticipation(new ArrayList<>());
//	    }
//	    squadra.getParticipation().add(participation);
//
//	    participation.setSquadra(squadra);
//
//		
//		}
//	
//
//
//		public Participation getParticipationPerSquadra(Squadre squadra) {
//		for (Participation p : participation) {
//		if (p.getSquadra().equals(squadra)) {
//		return p;
//		}
//		}
//		return null;
//		}
	
	//@OneToMany(mappedBy = "classifica", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@OneToMany(mappedBy = "classifica",cascade = CascadeType.ALL)
    private List<Partite> partite; 
	
	
	
	
	
	
	

}