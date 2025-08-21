package com.epicode.progettofinaleepicode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epicode.progettofinaleepicode.entity.Championship;
import com.epicode.progettofinaleepicode.entity.Channel;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long>{
	
	boolean existsByName(String name);

}
