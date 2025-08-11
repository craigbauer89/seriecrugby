package com.epicode.progettofinaleepicode.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epicode.progettofinaleepicode.entity.Championship;
import com.epicode.progettofinaleepicode.entity.Classifica;
import com.epicode.progettofinaleepicode.entity.Season;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Long> {
	
	List<Season> findAllByOrderByYearDesc();

	boolean existsByYear(String year);

}
