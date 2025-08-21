package com.epicode.progettofinaleepicode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epicode.progettofinaleepicode.entity.Channel;
import com.epicode.progettofinaleepicode.entity.Stadium;

@Repository
public interface StadiumRepository extends JpaRepository<Stadium, Long>{
	
	boolean existsByName(String name);

}
