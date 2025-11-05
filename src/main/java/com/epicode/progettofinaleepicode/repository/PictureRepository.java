package com.epicode.progettofinaleepicode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epicode.progettofinaleepicode.entity.Jersey;
import com.epicode.progettofinaleepicode.entity.Picture;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long>{
	
	public Jersey findByName(String name);
	public boolean existsByName(String name);

	
	
}
