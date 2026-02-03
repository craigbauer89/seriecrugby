package com.epicode.progettofinaleepicode.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
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
import com.epicode.progettofinaleepicode.entity.PartiteDto;

import com.epicode.progettofinaleepicode.service.PartiteService;


import lombok.AllArgsConstructor;


@RestController
@AllArgsConstructor
@RequestMapping("/")
@CrossOrigin
public class PartiteController {

	private PartiteService  partiteService;
	
	@GetMapping("partite")
	public ResponseEntity<List<Partite>> getAll() {
		return ResponseEntity.ok(partiteService.getAll());
	}
	
	@GetMapping("partite/by-year/{year}")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<Partite>> getAllByYear(@PathVariable Integer year) {
		return ResponseEntity.ok(partiteService.getAllByYear(year));
	}

@GetMapping("partite/by-season/{start}/{end}")
	public ResponseEntity<List<Partite>> getAllBySeason(
	        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
	        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
	    
	    LocalDateTime startDateTime = start.atStartOfDay();
	    LocalDateTime endDateTime = end.atTime(23, 59, 59);

	    return ResponseEntity.ok(partiteService.getAllBySeason(start, end));
	}


	
	@GetMapping("partite/by-results/{championship_id}")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<Partite>> getAllByChampResults(@PathVariable Long championship_id) {
		return ResponseEntity.ok(partiteService.getAllByChampResults(championship_id));
	}
	
	@GetMapping("partite/by-fixtures/{championship_id}")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<Partite>> getAllByChamFixtures(@PathVariable Long championship_id) {
		return ResponseEntity.ok(partiteService.getAllByChampFixtures(championship_id));
	}
	
	@GetMapping("partite/by-squadra/{squadra_id}")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<Partite>> getAllBySquadra(@PathVariable Long squadra_id) {
		return ResponseEntity.ok(partiteService.getAllBySquadra( squadra_id));
	}
	
	@GetMapping("partite/by-classifica/{classifica_id}")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<Partite>> getAllByClassifica(@PathVariable Long classifica_id) {
		return ResponseEntity.ok(partiteService.getAllByClassifica( classifica_id));
	}
	
	@PostMapping("partite/{channel_id}/{staidum_id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Partite> insert(@RequestBody PartiteDto dto, @PathVariable Long channel_id, @PathVariable Long staidum_id) {
		return ResponseEntity.ok(partiteService.insert(dto,channel_id,staidum_id));
	}

	@GetMapping("partite/{id}")
	public ResponseEntity<Optional<Partite>> getById(@PathVariable Long id) {
		return ResponseEntity.ok(partiteService.getById(id));
	}
	
	@PutMapping("partite/{id}/{channel_id}/{staidum_id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Partite> update(@PathVariable Long id,@RequestBody PartiteDto dto, @PathVariable Long channel_id, @PathVariable Long staidum_id) {
		return ResponseEntity.ok(partiteService.update(id, dto,channel_id,staidum_id));
	}
	
	@DeleteMapping("partite/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		partiteService.cancella(id);
		return ResponseEntity.ok("Partite cancellato");

	}
	

	
}

