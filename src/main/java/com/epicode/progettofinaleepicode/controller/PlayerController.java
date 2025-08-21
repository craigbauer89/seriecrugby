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
import com.epicode.progettofinaleepicode.entity.Player;
import com.epicode.progettofinaleepicode.entity.PlayerDTO;
import com.epicode.progettofinaleepicode.service.ChampionshipService;
import com.epicode.progettofinaleepicode.service.PlayerService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/player")
@CrossOrigin
public class PlayerController {
	
	private PlayerService  playerService;
	
	@GetMapping
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<Player>> getAll() {
		return ResponseEntity.ok(playerService.getAll());
	}
	
	
	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Player> insert(@RequestBody PlayerDTO dto) {
		return ResponseEntity.ok(playerService.insert(dto));
	}
	
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<Optional<Player>> getById(@PathVariable Long id) {
		return ResponseEntity.ok(playerService.getById(id));
	}
	
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Player> update(@PathVariable Long id,@RequestBody PlayerDTO dto) {
		return ResponseEntity.ok(playerService.update(id, dto));
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		playerService.cancella(id);
		return ResponseEntity.ok("Player cancellato");
	}

}
