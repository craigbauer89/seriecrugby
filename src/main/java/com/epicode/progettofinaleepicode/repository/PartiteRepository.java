package com.epicode.progettofinaleepicode.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.epicode.progettofinaleepicode.entity.Classifica;
import com.epicode.progettofinaleepicode.entity.Partite;
import com.epicode.progettofinaleepicode.entity.Squadre;

@Repository
public interface PartiteRepository extends JpaRepository<Partite, Long> {
	
	@Query("""
		    SELECT p FROM Partite p
		    WHERE (:date IS NULL OR p.date = :date)
		      AND (
		        p.squadra1 IN (
		            SELECT s FROM Classifica c
		            JOIN c.squadre s
		            WHERE c.name = :classificaName AND c.championship.name = :championshipName
		        )
		        OR
		        p.squadra2 IN (
		            SELECT s FROM Classifica c
		            JOIN c.squadre s
		            WHERE c.name = :classificaName AND c.championship.name = :championshipName
		        )
		      )
		""")
		List<Partite> findFilteredPartite(
		    @Param("date") LocalDate date,
		    @Param("classificaName") String classificaName,
		    @Param("championshipName") String championshipName
		);

	@Query(value = "SELECT * FROM partite WHERE EXTRACT(YEAR FROM date) = :year", nativeQuery = true)
	List<Partite> getAllByYear(@Param("year")Integer year);
	

	@Query(value = "SELECT * FROM partite p WHERE p.date >= :start AND p.date <= :end", nativeQuery = true)
	List<Partite> findBySeason(@Param("start") LocalDate start,
	                           @Param("end") LocalDate end);
	
	@Query(value = "SELECT * FROM partite WHERE date >= CURRENT_DATE", nativeQuery = true)
	List<Partite> getFixtures();
	
	@Query(value = "SELECT * FROM partite WHERE date <= CURRENT_DATE", nativeQuery = true)
	List<Partite> getResults();
	
	
	@Query(value = "SELECT p.* FROM public.partite JOIN public.classifica cl ON cl.id = p.main_classifica_id JOIN public.championship c ON c.id = cl.championship_id WHERE p.date >= CURRENT_DATE AND c.id = :championship_id", nativeQuery = true)
	List<Partite> getChampFixtures(@Param("championship_id")Long championship_id);
	
	@Query(value = "SELECT p.* FROM public.partite JOIN public.classifica cl ON cl.id = p.main_classifica_id JOIN public.championship c ON c.id = cl.championship_id WHERE p.date <= CURRENT_DATE AND c.id = :championship_id", nativeQuery = true)
	List<Partite> getChampResults(@Param("championship_id")Long championship_id);

	
	@Query(value = "SELECT * FROM partite WHERE squadra1_id = :squadra_id OR squadra2_id = :squadra_id ORDER BY date DESC", nativeQuery = true)
	List<Partite> getAllBySquadra(@Param("squadra_id")Long squadra_id);
	
	@Query(value = "SELECT * FROM partite WHERE main_classifica_id = :classifica_id  ORDER BY date DESC", nativeQuery = true)
	List<Partite> getAllByClassifica(@Param("classifica_id")Long classifica_id);
	
	
}
