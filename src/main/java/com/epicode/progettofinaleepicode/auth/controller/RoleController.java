package com.epicode.progettofinaleepicode.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epicode.progettofinaleepicode.auth.entity.Role;
import com.epicode.progettofinaleepicode.auth.entity.Utente;
import com.epicode.progettofinaleepicode.auth.service.RoleService;
import com.epicode.progettofinaleepicode.auth.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/roles")
@CrossOrigin
public class RoleController {

	private RoleService  roleService;
	
	
	
	@PostMapping
	public ResponseEntity<Role> insert(@RequestBody Role role) {
		return ResponseEntity.ok(roleService.insert(role));
	}

	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		roleService.cancella(id);
		return ResponseEntity.ok("Role cancellato");

	}
	

	
}
