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

import com.epicode.progettofinaleepicode.entity.Partite;
import com.epicode.progettofinaleepicode.entity.PartiteDto;

import com.epicode.progettofinaleepicode.service.PartiteService;


import lombok.AllArgsConstructor;


@RestController
@AllArgsConstructor
@RequestMapping("/partite")
@CrossOrigin
public class PartiteController {

	private PartiteService  partiteService;
	
	@GetMapping
	public ResponseEntity<List<Partite>> getAll() {
		return ResponseEntity.ok(partiteService.getAll());
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Partite> insert(@RequestBody PartiteDto dto) {
		return ResponseEntity.ok(partiteService.insert(dto));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Partite>> getById(@PathVariable Long id) {
		return ResponseEntity.ok(partiteService.getById(id));
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Partite> update(@PathVariable Long id,@RequestBody PartiteDto dto) {
		return ResponseEntity.ok(partiteService.update(id, dto));
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		partiteService.cancella(id);
		return ResponseEntity.ok("Partite cancellato");

	}
	

	
}

