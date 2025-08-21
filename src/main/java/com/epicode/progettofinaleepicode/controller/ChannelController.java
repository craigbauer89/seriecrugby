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
import com.epicode.progettofinaleepicode.entity.News;
import com.epicode.progettofinaleepicode.service.ChampionshipService;
import com.epicode.progettofinaleepicode.service.ChannelService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/channel")
@CrossOrigin
public class ChannelController {
	
	private ChannelService  channelService;
	
	
	@GetMapping
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<Channel>> getAll() {
		return ResponseEntity.ok(channelService.getAll());
	}
	
	
	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Channel> insert(@RequestBody ChannelDto dto) {
		return ResponseEntity.ok(channelService.insert(dto));
	}
	
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<Optional<Channel>> getById(@PathVariable Long id) {
		return ResponseEntity.ok(channelService.getById(id));
	}
	
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Channel> update(@PathVariable Long id,@RequestBody ChannelDto dto) {
		return ResponseEntity.ok(channelService.update(id, dto));
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		channelService.cancella(id);
		return ResponseEntity.ok("Channel cancellato");
	}

}
