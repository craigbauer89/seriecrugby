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
import com.epicode.progettofinaleepicode.entity.News;
import com.epicode.progettofinaleepicode.entity.NewsDto;
import com.epicode.progettofinaleepicode.service.ChampionshipService;
import com.epicode.progettofinaleepicode.service.NewsService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/news")
@CrossOrigin
public class NewsController {
	
	private NewsService  newsService;

	@GetMapping
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<News>> getAll() {
		return ResponseEntity.ok(newsService.getAll());
	}
	
	
	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<News> insert(@RequestBody NewsDto dto, @PathVariable Long season_id) {
		return ResponseEntity.ok(newsService.insert(dto));
	}
	
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<Optional<News>> getById(@PathVariable Long id) {
		return ResponseEntity.ok(newsService.getById(id));
	}
	
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<News> update(@PathVariable Long id,@RequestBody NewsDto dto) {
		return ResponseEntity.ok(newsService.update(id, dto));
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		newsService.cancella(id);
		return ResponseEntity.ok("News cancellato");
	}

}
