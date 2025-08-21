package com.epicode.progettofinaleepicode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epicode.progettofinaleepicode.entity.Channel;
import com.epicode.progettofinaleepicode.entity.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long>{
	
	boolean existsByName(String name);


}
