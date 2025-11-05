package com.epicode.progettofinaleepicode.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.epicode.progettofinaleepicode.entity.Squadre;

@Repository
public interface SquadreRepository extends JpaRepository<Squadre, Long> {
	
	public Squadre findByNome(String nome);
	public boolean existsByNome(String nome);
	
	@Query("SELECT s FROM Squadre s ORDER BY s.punti DESC, s.differenza DESC")
    List<Squadre> getAllOrdered();
	
	@Query("SELECT s FROM Squadre s JOIN s.classifica c WHERE c.id = :classificaid  ORDER BY s.punti DESC, s.differenza DESC")
    List<Squadre> getByClassifica(@Param("classificaid") Long classificaid);
	
	@Query("SELECT s FROM Squadre s ORDER BY s.nome ")
    List<Squadre> getAllOrderedAlpha();
	
	@Query("SELECT s FROM Squadre s LEFT JOIN FETCH s.participation WHERE s.id = :id")
	Optional<Squadre> findByIdWithParticipation(@Param("id") Long id);
	
	@Query("SELECT s FROM Squadre s LEFT JOIN FETCH s.participation")
	List<Squadre> findAllWithParticipation();


}
