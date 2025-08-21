package com.epicode.progettofinaleepicode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epicode.progettofinaleepicode.entity.Channel;
import com.epicode.progettofinaleepicode.entity.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
	
	boolean existsByTitle(String title);

}
