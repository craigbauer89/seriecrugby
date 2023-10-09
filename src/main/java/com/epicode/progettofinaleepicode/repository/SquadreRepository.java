package com.epicode.progettofinaleepicode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.epicode.progettofinaleepicode.entity.Squadre;

import java.util.List;

@Repository
public interface SquadreRepository extends JpaRepository<Squadre, Long> {
	
	public Squadre findByNome(String nome);
	public boolean existsByNome(String nome);

	@Query("SELECT s FROM Squadre s ORDER BY s.punti DESC, s.differenza DESC")
	List<Squadre> getAllOrdered();

	@Query("SELECT s FROM Squadre s ORDER BY s.nome ")
	List<Squadre> getAllOrderedAlpha();

}
