package com.epicode.progettofinaleepicode.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epicode.progettofinaleepicode.entity.Classifica;
import com.epicode.progettofinaleepicode.entity.ClassificaDto;
import com.epicode.progettofinaleepicode.entity.Season;
import com.epicode.progettofinaleepicode.entity.SeasonDto;
import com.epicode.progettofinaleepicode.service.ClassificaService;
import com.epicode.progettofinaleepicode.service.SeasonService;
import com.epicode.progettofinaleepicode.service.SquadreService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/season")
@CrossOrigin
public class SeasonController {
	
private SeasonService  seasonService;
	
	@GetMapping
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<Season>> getAll() {
		return ResponseEntity.ok(seasonService.getAll());
	}
	
	
	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Season> insert(@RequestBody SeasonDto dto) {
		return ResponseEntity.ok(seasonService.insert(dto));
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<Season> getById(@PathVariable Long id) {
		return ResponseEntity.ok(seasonService.getById(id));
	}
	
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Season> update(@PathVariable Long id,@RequestBody SeasonDto dto) {
		return ResponseEntity.ok(seasonService.update(id, dto));
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		seasonService.cancella(id);
		return ResponseEntity.ok("Season cancellato");
	}
	

}
