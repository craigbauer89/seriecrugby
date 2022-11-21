package com.epicode.progettofinaleepicode.auth.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
//import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Table(name="roles")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	
//	@Column(length = 20)
	@Enumerated(EnumType.STRING)
	private ERole roleName;
	
	
}
