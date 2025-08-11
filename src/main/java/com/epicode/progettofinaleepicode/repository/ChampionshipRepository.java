package com.epicode.progettofinaleepicode.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.epicode.progettofinaleepicode.entity.Championship;


@Repository
public interface ChampionshipRepository extends JpaRepository<Championship, Long>{
	
	
	List<Championship>findAllByOrderByNameAsc();
	
	@Query("SELECT c FROM Championship c JOIN c.season s WHERE s.id = :seasonid ORDER BY c.name ASC")
	List<Championship> findBySeasonId(@Param("seasonid") Long seasonid);
	

	boolean existsByName(String name);
	
	boolean existsByNameAndSeasonId(String name, Long seasonId);

}
