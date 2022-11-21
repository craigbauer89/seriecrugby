package com.epicode.progettofinaleepicode.auth.service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import com.epicode.progettofinaleepicode.auth.entity.Role;
import com.epicode.progettofinaleepicode.auth.repository.RoleRepository;
import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
//@Validated
public class RoleService {

	private RoleRepository  roleRepository;
	

	public Role insert(Role role) {
		if(roleRepository.existsById(role.getId())) {
			throw new EntityExistsException("Role gia inserito");
		}
		
		return roleRepository.save(role);
		
	}

		public void cancella(Long id) {
			if (!roleRepository.existsById(id)) {
				throw new EntityNotFoundException("Role not trovato");
			}
			
			roleRepository.deleteById(id);
		}

}



