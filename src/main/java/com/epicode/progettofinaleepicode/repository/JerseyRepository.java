package com.epicode.progettofinaleepicode.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epicode.progettofinaleepicode.entity.Jersey;




@Repository
public interface JerseyRepository extends JpaRepository<Jersey, Long> {
	
	public Jersey findByColor(String color);
	public boolean existsByColor(String color);

}
