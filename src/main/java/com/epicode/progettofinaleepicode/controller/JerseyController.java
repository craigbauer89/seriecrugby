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

import com.epicode.progettofinaleepicode.entity.Jersey;
import com.epicode.progettofinaleepicode.entity.JerseyDto;
import com.epicode.progettofinaleepicode.entity.Partite;
import com.epicode.progettofinaleepicode.entity.PartiteDto;
import com.epicode.progettofinaleepicode.service.JerseyService;
import com.epicode.progettofinaleepicode.service.PartiteService;

import lombok.AllArgsConstructor;




@RestController
@AllArgsConstructor
@RequestMapping("/jersey")
@CrossOrigin
public class JerseyController {

	private JerseyService  jerseyService;
	
	@GetMapping
	public ResponseEntity<List<Jersey>> getAll() {
		return ResponseEntity.ok(jerseyService.getAll());
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Jersey> insert(@RequestBody JerseyDto dto) {
		return ResponseEntity.ok(jerseyService.insert(dto));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Jersey>> getById(@PathVariable Long id) {
		return ResponseEntity.ok(jerseyService.getById(id));
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Jersey> update(@PathVariable Long id,@RequestBody JerseyDto dto) {
		return ResponseEntity.ok(jerseyService.update(id, dto));
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		jerseyService.cancella(id);
		return ResponseEntity.ok("Jersey cancellato");

	}
	

	
}

