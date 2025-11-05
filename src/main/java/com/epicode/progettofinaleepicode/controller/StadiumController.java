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
import com.epicode.progettofinaleepicode.entity.Stadium;
import com.epicode.progettofinaleepicode.entity.StadiumDto;
import com.epicode.progettofinaleepicode.service.ChampionshipService;
import com.epicode.progettofinaleepicode.service.StadiumService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/stadium")
@CrossOrigin
public class StadiumController {
	
	private StadiumService  stadiumService;
	
	@GetMapping
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<Stadium>> getAll() {
		return ResponseEntity.ok(stadiumService.getAll());
	}
	
	
	@PostMapping("/{picture_id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Stadium> insert(@RequestBody StadiumDto dto,@PathVariable Long picture_id) {
		return ResponseEntity.ok(stadiumService.insert(dto,picture_id));
	}

	
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<Optional<Stadium>> getById(@PathVariable Long id) {
		return ResponseEntity.ok(stadiumService.getById(id));
	}
	
	
	@PutMapping("/{id}/{picture_id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Stadium> update(@PathVariable Long id,@RequestBody StadiumDto dto,@PathVariable Long picture_id) {
		return ResponseEntity.ok(stadiumService.update(id, dto,picture_id));
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		stadiumService.cancella(id);
		return ResponseEntity.ok("Stadium cancellato");
	}

}
