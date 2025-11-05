package com.epicode.progettofinaleepicode.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.epicode.progettofinaleepicode.entity.Championship;
import com.epicode.progettofinaleepicode.entity.Classifica;
import com.epicode.progettofinaleepicode.entity.Season;
import com.epicode.progettofinaleepicode.entity.Squadre;


@Repository
public interface ClassificaRepository extends JpaRepository<Classifica, Long> {

	List<Classifica> findAllByOrderByNameAsc();
	
	@Query("SELECT c FROM Classifica c JOIN c.championship ch WHERE ch.id = :championshipid")
	List<Classifica> findByChampionshipId(@Param("championshipid") Long championshipid);

	@Query("SELECT c FROM Classifica c " +
		       "JOIN c.squadre s " +
		       "WHERE s.id = :squadraId AND c.championship.season.year = :anno")
		List<Classifica> findBySquadraIdAndSeasonYear(@Param("squadraId") Long squadraId,
		                                              @Param("anno") String anno);

	@Query("SELECT c FROM Classifica c LEFT JOIN FETCH c.participation WHERE c.id = :id")
	Optional<Classifica> findByIdWithPartecipition(@Param("id") Long id);

	@Query("SELECT c FROM Classifica c " +
		       "JOIN c.squadre s " +
		       "WHERE s.id = :squadraId")
		List<Classifica> findClassificheBySquadraId(@Param("squadraId") Long squadraId);



	
	boolean existsByName(String name);

}
