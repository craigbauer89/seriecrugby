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

import com.epicode.progettofinaleepicode.entity.Channel;
import com.epicode.progettofinaleepicode.entity.ChannelDto;
import com.epicode.progettofinaleepicode.entity.LoadIds;
import com.epicode.progettofinaleepicode.entity.LoadIdsDto;
import com.epicode.progettofinaleepicode.service.ChannelService;
import com.epicode.progettofinaleepicode.service.LoadIdsService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/loadIds")
@CrossOrigin
public class LoadIdsController {
	
	private LoadIdsService  loadIdsService;
	
	@GetMapping
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<LoadIds>> getAll() {
		return ResponseEntity.ok(loadIdsService.getAll());
	}
	
	
	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<LoadIds> insert(@RequestBody LoadIdsDto dto) {
		return ResponseEntity.ok(loadIdsService.insert(dto));
	}
	
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<Optional<LoadIds>> getById(@PathVariable Long id) {
		return ResponseEntity.ok(loadIdsService.getById(id));
	}
	
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<LoadIds> update(@PathVariable Long id,@RequestBody LoadIdsDto dto) {
		return ResponseEntity.ok(loadIdsService.update(id, dto));
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		loadIdsService.cancella(id);
		return ResponseEntity.ok("LoadIds cancellato");
	}

	
	

}
