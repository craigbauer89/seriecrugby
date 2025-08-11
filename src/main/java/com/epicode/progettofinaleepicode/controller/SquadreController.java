package com.epicode.progettofinaleepicode.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

import com.epicode.progettofinaleepicode.entity.Championship;
import com.epicode.progettofinaleepicode.entity.Partite;
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
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<Squadre>> getAll() {
		return ResponseEntity.ok(squadreService.getAll());
	}
	
	@GetMapping("/by-classifica/{classificaid}")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<Squadre>> getByClassificaId(@PathVariable Long classificaid) {
		return ResponseEntity.ok(squadreService.getByClassificaId(classificaid));
	}
	
	@GetMapping("/sortedbruv")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<Squadre>> getAllAlpha() {
		return ResponseEntity.ok(squadreService.getAllAlpha());
	}
	
	
	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Squadre> insert(@RequestBody SquadreDto dto) {
		return ResponseEntity.ok(squadreService.insert(dto));
	}
	
	@PostMapping("/{id}/{jersey_id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Squadre> insertJersey(@PathVariable Long id,@PathVariable Long jersey_id ) {
		return ResponseEntity.ok(squadreService.insertJersey(id, jersey_id));
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<Optional<Squadre>> getById(@PathVariable Long id) {
		return ResponseEntity.ok(squadreService.getById(id));
	}
	
	@GetMapping("/{id}/home_partite")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<Partite>>getHomePartiteByID(@PathVariable Long id) {
		return ResponseEntity.ok(squadreService.getHomePartiteById(id));
	}
	@GetMapping("/{id}/away_partite")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<Partite>>getAwayPartiteById(@PathVariable Long id) {
		return ResponseEntity.ok(squadreService.getAwayPartiteById(id));
	}
	
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Squadre> update(@PathVariable Long id,@RequestBody SquadreDto dto) {
		return ResponseEntity.ok(squadreService.update(id, dto));
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		squadreService.cancella(id);
		return ResponseEntity.ok("Squadra cancellato");
	}
	
	@DeleteMapping("/{id}/jersey")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> deleteJersey(@PathVariable Long id) {
		squadreService.cancellaJersey(id);
		return ResponseEntity.ok("Jersey cancellato");
	}
	
}