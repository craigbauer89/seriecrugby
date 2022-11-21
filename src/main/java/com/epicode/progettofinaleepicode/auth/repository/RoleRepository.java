package com.epicode.progettofinaleepicode.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epicode.progettofinaleepicode.auth.entity.Role;



@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	public Optional<Role> findById(Long id);
	public boolean existsById(Long id);

}