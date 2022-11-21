package com.epicode.progettofinaleepicode.auth.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epicode.progettofinaleepicode.auth.config.JwtUtils;
import com.epicode.progettofinaleepicode.auth.entity.JwtResponse;
import com.epicode.progettofinaleepicode.auth.entity.Login;
import com.epicode.progettofinaleepicode.auth.entity.UserDetailsImpl;
import com.epicode.progettofinaleepicode.auth.entity.Utente;
import com.epicode.progettofinaleepicode.auth.repository.UserRepository;
import com.epicode.progettofinaleepicode.auth.service.UserService;



@RestController
@CrossOrigin(origins="*")
@RequestMapping("/auth")
public class LoginController {
	@Autowired
	AuthenticationManager authManager;
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	UserRepository userRepo;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody Login request) {
		
		UsernamePasswordAuthenticationToken usrNameAuth = new UsernamePasswordAuthenticationToken( 
				request.getUsername(), 
				request.getPassword()
				
		);
		System.out.println(request.getUsername());
		System.out.println(request.getPassword());
		Authentication authentication = authManager.authenticate(usrNameAuth);
		
		System.out.println(authentication);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities()
								.stream()
								.map(item -> item.getAuthority())
								.collect(Collectors.toList());
		Long id = userDetails.getId();
		
//		JwtResponse jwtresp = new JwtResponse(
//				jwt, 
//				userDetails.getId(), 
//				userDetails.getUsername(),
//				roles
//			);
		
		Utente user = userRepo.findById(id).get();
		JwtResponse jwtresp = new JwtResponse(user, jwt);
		
		return ResponseEntity.ok(jwtresp);
		
	}


}
