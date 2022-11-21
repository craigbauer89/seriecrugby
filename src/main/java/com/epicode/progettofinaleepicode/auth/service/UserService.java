package com.epicode.progettofinaleepicode.auth.service;



import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.epicode.progettofinaleepicode.auth.entity.Role;
import com.epicode.progettofinaleepicode.auth.entity.UserResponse;
import com.epicode.progettofinaleepicode.auth.entity.Utente;
import com.epicode.progettofinaleepicode.auth.entity.UtenteDto;
import com.epicode.progettofinaleepicode.auth.repository.RoleRepository;
import com.epicode.progettofinaleepicode.auth.repository.UserRepository;
import com.epicode.progettofinaleepicode.entity.Squadre;
import com.epicode.progettofinaleepicode.entity.SquadreDto;

import lombok.AllArgsConstructor;




@Service
@AllArgsConstructor

public class UserService {

	private UserRepository  userRepository;
	
	
	private RoleRepository  roleRepository;
	
	private ObjectProvider<Utente>utenteProvider;
	
	private ObjectProvider<UtenteDto> utenteDtoProvider;
	
	PasswordEncoder encoder;
	
	public List<Utente> getAll() {
		return userRepository.findAll();
	}
	

	public Utente insert(UtenteDto dto) {
		if(userRepository.existsByUsername(dto.getUsername())) {
			throw new EntityExistsException("Username gia inserito");
		}
		
		
		
		dto.setPassword(encoder.encode(dto.getPassword()));
		
		Utente user = utenteProvider.getObject();
		BeanUtils.copyProperties(dto, user);
		
		Role role = roleRepository.findById((long) 2).get();
		
		Set <Role> roles = new HashSet<>();
		roles.add(role);
		user.setRoles(roles);
		
		return userRepository.save(user);
		
	}
	
	

		public void cancella(Long id) {
			if (!userRepository.existsById(id)) {
				throw new EntityNotFoundException("User not trovato");
			}
			
			userRepository.deleteById(id);
		}
		
		
		public List<UserResponse> getAllUsersBasicInformations() {
			return userRepository.findAll()
					.stream()
					.map( user ->  UserResponse
									.builder()
									.userName(  user.getUsername()  )
									.role( user.getRoles().stream().findFirst().get().getRoleName().name() )
									.build()   
					).collect(Collectors.toList());
		}
		
		public UserResponse getUserBasicInformations(String userName) {
			Utente user = userRepository.findByUsername(userName).get();
			
			
			
			return UserResponse
			.builder()
			.userName(userName)
			.role( user.getRoles().stream().findFirst().get().getRoleName().name()).build();
			
		}

}



