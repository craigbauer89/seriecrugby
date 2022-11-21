package com.epicode.progettofinaleepicode.auth.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.epicode.progettofinaleepicode.auth.entity.Utente;
 

@Repository
public interface UserRepository extends JpaRepository<Utente, Long> {
	
	public Optional<Utente> findById(Long id);
	public boolean existsById(Long id);
	public boolean existsByUsername(String username);
	
	
	Optional<Utente> findByUsername(String username);

	

}
