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

import com.epicode.progettofinaleepicode.entity.Championship;
import com.epicode.progettofinaleepicode.entity.ChampionshipDto;
import com.epicode.progettofinaleepicode.entity.Classifica;
import com.epicode.progettofinaleepicode.entity.ClassificaDto;
import com.epicode.progettofinaleepicode.service.ChampionshipService;
import com.epicode.progettofinaleepicode.service.SquadreService;

import lombok.AllArgsConstructor;


@RestController
@AllArgsConstructor
@RequestMapping("/championship")
@CrossOrigin
public class ChampionshipController {
	
private ChampionshipService  championshipService;
	
	@GetMapping
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<Championship>> getAll() {
		return ResponseEntity.ok(championshipService.getAll());
	}
	
	@GetMapping("/by-season/{seasonid}")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<Championship>> getBySeasonId(@PathVariable Long seasonid) {
		return ResponseEntity.ok(championshipService.getBySeasonId(seasonid));
	}
	
	@PostMapping("/{season_id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Championship> insert(@RequestBody ChampionshipDto dto, @PathVariable Long season_id) {
		return ResponseEntity.ok(championshipService.insert(dto, season_id));
	}
	
//	@PostMapping("/{championship_id}/{season_id}")
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
//	public ResponseEntity<String> insert(@PathVariable Long championship_id, @PathVariable Long season_id) {
//		championshipService.addChampionshipSeason(championship_id, season_id);
//		return ResponseEntity.ok("Championship Season aggiunto");
//	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<Optional<Championship>> getById(@PathVariable Long id) {
		return ResponseEntity.ok(championshipService.getById(id));
	}
	
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Championship> update(@PathVariable Long id,@RequestBody ChampionshipDto dto) {
		return ResponseEntity.ok(championshipService.update(id, dto));
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		championshipService.cancella(id);
		return ResponseEntity.ok("Championship cancellato");
	}
	
//	@DeleteMapping("/{championship_id}/{season_id}")
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
//	public ResponseEntity<String> delete(@PathVariable Long championship_id, @PathVariable Long season_id) {
//		championshipService.rimouvereChampionshipSeason(championship_id, season_id);
//		return ResponseEntity.ok("Championship Season rimosto");
//	}
}
