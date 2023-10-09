package com.epicode.progettofinaleepicode.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


import com.epicode.progettofinaleepicode.entity.Squadre;
import com.epicode.progettofinaleepicode.entity.SquadreDto;
import com.epicode.progettofinaleepicode.service.SquadreService;

import lombok.AllArgsConstructor;


@RestController
@AllArgsConstructor
@RequestMapping("/squadre")
@CrossOrigin
public class SquadreController {

	private SquadreService  squadreService;
	
	@GetMapping
	public ResponseEntity<List<Squadre>> getAll() {
		return ResponseEntity.ok(squadreService.getAll());
	}

	@GetMapping("/sorted")
	public ResponseEntity<List<Squadre>> getAllAlpha() {
		return ResponseEntity.ok(squadreService.getAllAlpha());
	}
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Squadre> insert(@RequestBody SquadreDto dto) {
		return ResponseEntity.ok(squadreService.insert(dto));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Squadre>> getById(@PathVariable Long id) {
		return ResponseEntity.ok(squadreService.getById(id));
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Squadre> update(@PathVariable Long id,@RequestBody SquadreDto dto) {
		return ResponseEntity.ok(squadreService.update(id, dto));
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		squadreService.cancella(id);
		return ResponseEntity.ok("Squadra cancellato");
	}
	
}