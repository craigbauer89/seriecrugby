

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epicode.progettofinaleepicode.entity.Championship;
import com.epicode.progettofinaleepicode.entity.Classifica;
import com.epicode.progettofinaleepicode.entity.ClassificaDto;
import com.epicode.progettofinaleepicode.entity.Partite;
import com.epicode.progettofinaleepicode.entity.Squadre;
import com.epicode.progettofinaleepicode.entity.SquadreDto;
import com.epicode.progettofinaleepicode.service.ClassificaService;
import com.epicode.progettofinaleepicode.service.SquadreService;

import lombok.AllArgsConstructor;


@RestController
@AllArgsConstructor
@RequestMapping("/classifica")
@CrossOrigin
public class ClassificaController {

	private ClassificaService  classificaService;
	
	@GetMapping
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<Classifica>> getAll() {
		return ResponseEntity.ok(classificaService.getAll());
	}
	
	@GetMapping("/by-championship/{championshipid}")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<Classifica>> getBySeasonId(@PathVariable Long championshipid) {
		return ResponseEntity.ok(classificaService.getByChampionshipId(championshipid));
	}
	
	@GetMapping("/by-squadra/{squadraid}/{anno}")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<Classifica>> getBySquadraId(@PathVariable Long squadraid,@PathVariable  String anno) {
		return ResponseEntity.ok(classificaService.getBySquadraId(squadraid, anno));
	}
	
	@PostMapping("/{championship_id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Classifica> insert(@RequestBody ClassificaDto dto, @PathVariable Long championship_id) {
		return ResponseEntity.ok(classificaService.insert(dto,championship_id));
	}
	
	@PostMapping("/{classifica_id}/{squadra_id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Classifica> insertSquadra(@PathVariable Long classifica_id,@PathVariable Long squadra_id) {
		return ResponseEntity.ok(classificaService.insertSquadra(classifica_id, squadra_id));
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<Optional<Classifica>> getById(@PathVariable Long id) {
		return ResponseEntity.ok(classificaService.getById(id));
	}
	
	
	@PutMapping("/{id}/{championship_id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Classifica> update(@PathVariable Long id,@RequestBody ClassificaDto dto,@PathVariable Long championship_id ) {
		return ResponseEntity.ok(classificaService.update(id, dto, championship_id));
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		classificaService.cancella(id);
		return ResponseEntity.ok("Classifica cancellato");
	}
	
	@DeleteMapping("/{classifica_id}/{squadra_id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> deleteSquadra(@PathVariable Long classifica_id, @PathVariable Long squadra_id) {
		classificaService.cancellaSquadre( classifica_id, squadra_id);
		return ResponseEntity.ok("Squadra cancellato dalla Classifica");
	}
	
}