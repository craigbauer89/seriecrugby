package com.epicode.progettofinaleepicode.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

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
public class Stadium {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String indirizzo;
	private String picture;
	private String allenatore;
	private String sito;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private String name;
	private String telefono;

	@OneToOne(mappedBy = "stadium")
	private Squadre squadra;
	

}
