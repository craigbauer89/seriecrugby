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

import com.epicode.progettofinaleepicode.entity.News;
import com.epicode.progettofinaleepicode.entity.NewsDto;
import com.epicode.progettofinaleepicode.entity.Participation;
import com.epicode.progettofinaleepicode.entity.ParticipationDto;
import com.epicode.progettofinaleepicode.service.NewsService;
import com.epicode.progettofinaleepicode.service.ParticipationService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/participation")
@CrossOrigin
public class ParticipationController {


	private ParticipationService  participationService;

	@GetMapping
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<Participation>> getAll() {
		return ResponseEntity.ok(participationService.getAll());
	}
	
	@GetMapping("/classifica/{id}")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<Participation>> getAllByClassifica(@PathVariable Long id) {
		return ResponseEntity.ok(participationService.getAllByClassifica(id));
	}
	
	
	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Participation> insert(@RequestBody ParticipationDto dto) {
		return ResponseEntity.ok(participationService.insert(dto));
	}
	

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<Optional<Participation>> getById(@PathVariable Long id) {
		return ResponseEntity.ok(participationService.getById(id));
	}
	
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Participation> update(@PathVariable Long id,@RequestBody ParticipationDto dto) {
		return ResponseEntity.ok(participationService.update(id, dto));
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		participationService.cancella(id);
		return ResponseEntity.ok("Participation cancellato");
	}

}
