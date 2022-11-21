package com.epicode.progettofinaleepicode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epicode.progettofinaleepicode.entity.Squadre;

@Repository
public interface SquadreRepository extends JpaRepository<Squadre, Long> {
	
	public Squadre findByNome(String nome);
	public boolean existsByNome(String nome);

}
