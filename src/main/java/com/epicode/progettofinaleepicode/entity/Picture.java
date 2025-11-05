package com.epicode.progettofinaleepicode.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler", "news","player","stadium"})
public class Picture {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	//@OneToMany(mappedBy = "picture")
    //private List<News> news; 
	
	//@OneToMany(mappedBy = "picture")
    //private List<Player> player; 
	
	//@OneToMany(mappedBy = "picture")
    //private List<Stadium> stadium; 

}
