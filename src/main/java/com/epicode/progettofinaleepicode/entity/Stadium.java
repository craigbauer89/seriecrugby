package com.epicode.progettofinaleepicode.entity;

import java.math.BigDecimal;
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
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"squadre","partite"})
public class Stadium {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String indirizzo;
	
	@ManyToOne 
	@JoinColumn(name="picture_id")
	private Picture picture;
	
	private String allenatore;
	private String sito;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private String name;
	private String telefono;

	@OneToMany(mappedBy = "stadium") // no cascade needed if you're saving via Squadre
    private List<Partite> partite = new ArrayList<>();
	
	@OneToMany(mappedBy = "stadium") // no cascade needed if you're saving via Squadre
    private List<Squadre> squadre = new ArrayList<>();
	

}
