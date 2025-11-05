package com.epicode.progettofinaleepicode.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StadiumDto {

	private String indirizzo;	
	private String allenatore;
	private String sito;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private String name;
	private String telefono;

}
