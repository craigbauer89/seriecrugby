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

import com.epicode.progettofinaleepicode.entity.Jersey;
import com.epicode.progettofinaleepicode.entity.JerseyDto;
import com.epicode.progettofinaleepicode.entity.Picture;
import com.epicode.progettofinaleepicode.entity.PictureDTO;
import com.epicode.progettofinaleepicode.service.JerseyService;
import com.epicode.progettofinaleepicode.service.PictureService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/picture")
@CrossOrigin
public class PictureController {

private PictureService  pictureService;
	
	@GetMapping
	public ResponseEntity<List<Picture>> getAll() {
		return ResponseEntity.ok(pictureService.getAll());
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Picture> insert(@RequestBody PictureDTO dto) {
		return ResponseEntity.ok(pictureService.insert(dto));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Picture>> getById(@PathVariable Long id) {
		return ResponseEntity.ok(pictureService.getById(id));
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Picture> update(@PathVariable Long id,@RequestBody PictureDTO dto) {
		return ResponseEntity.ok(pictureService.update(id, dto));
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		pictureService.cancella(id);
		return ResponseEntity.ok("Picture cancellato");

	}
	

	
}
