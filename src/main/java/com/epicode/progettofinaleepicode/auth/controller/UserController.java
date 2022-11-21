package com.epicode.progettofinaleepicode.auth.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epicode.progettofinaleepicode.auth.entity.Utente;
import com.epicode.progettofinaleepicode.auth.entity.UtenteDto;
import com.epicode.progettofinaleepicode.auth.service.UserService;
import com.epicode.progettofinaleepicode.entity.Partite;
import com.epicode.progettofinaleepicode.entity.PartiteDto;
import com.epicode.progettofinaleepicode.service.PartiteService;

import lombok.AllArgsConstructor;


@RestController
@AllArgsConstructor
@RequestMapping("/users")
@CrossOrigin
public class UserController {

	private UserService  userService;
	
	@GetMapping
	public ResponseEntity<List<Utente>> getAll() {
		return ResponseEntity.ok(userService.getAll());
	}
	
	
	@PostMapping
	public ResponseEntity<Utente> insert(@RequestBody UtenteDto user) {
		return ResponseEntity.ok(userService.insert(user));
	}

	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		userService.cancella(id);
		return ResponseEntity.ok("User cancellato");

	}
	

	
}
