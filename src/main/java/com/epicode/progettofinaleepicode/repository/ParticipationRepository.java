package com.epicode.progettofinaleepicode.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.epicode.progettofinaleepicode.entity.Classifica;
import com.epicode.progettofinaleepicode.entity.Participation;


@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Long> {
	
    boolean existsBySquadraIdAndClassificaId(Long squadraId, Long classificaId);
    
    @Query("SELECT p FROM Participation p " +
    	       "JOIN p.squadra s " +
    	       "JOIN p.classifica c " +
    	       "WHERE s.id = :squadraId AND c.id = :classificaId")
    	Participation findBySquadraIdAndClassificaId(@Param("squadraId") Long squadraId,
    	                                             @Param("classificaId") Long classificaId);


    @Query("SELECT p FROM Participation p " +
 	       "JOIN p.classifica c " +
 	       "WHERE c.id = :classificaId ORDER BY p.punti DESC, p.differenza DESC")
 	List<Participation> findByClassificaId(@Param("classificaId") Long classificaId);



}
