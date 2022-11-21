package com.epicode.progettofinaleepicode.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.epicode.progettofinaleepicode.entity.Partite;
import com.epicode.progettofinaleepicode.entity.PartiteDto;

import com.epicode.progettofinaleepicode.repository.PartiteRepository;


import lombok.AllArgsConstructor;




@Service
@AllArgsConstructor
//@Validated
public class PartiteService {

	private PartiteRepository  partiteRepository;
	
	private ObjectProvider<Partite> partiteProvider;
	
	private ObjectProvider<PartiteDto> partiteDtoProvider;

	public List<Partite> getAll() {
		return partiteRepository.findAll();
	}
	
	public Optional<Partite> getById(Long id) {
		Optional<Partite>  partita = partiteRepository.findById(id);
		if (partita.isPresent()) return partita;
		
		throw new EntityNotFoundException("Partita non trovato");
				
	}
	
	public Partite getById2(Long id) {
		if(partiteRepository.existsById(id)) {
			return partiteRepository.findById(id).get();
		}
		throw new EntityNotFoundException("Partita non trovato");
	}
	
//	public Partite insert(@Valid PartiteDto dto) {
	public Partite insert(PartiteDto dto) {
//		if(partiteRepository.existsByDate(dto.getDate())) {
//			throw new EntityExistsException("Partita gia inserito");
//		}
		
		Partite partita = partiteProvider.getObject();
		BeanUtils.copyProperties(dto, partita);
		
		return partiteRepository.save(partita);
		
	}
	
//	public Partite update(Long id, @Valid PartiteDto dto) {
		public Partite update(Long id, PartiteDto dto) {
		Optional<Partite> partitaUpdate = partiteRepository.findById(id);
		if (!partitaUpdate.isPresent()) {
			throw new EntityNotFoundException();	
		}
		
		Partite partita = partitaUpdate.get();
		BeanUtils.copyProperties(dto, partita);
		
		return partiteRepository.save(partita);
		
	}
		
		public void cancella(Long id) {
			if (!partiteRepository.existsById(id)) {
				throw new EntityNotFoundException("Partite not trovato");
			}
			
			partiteRepository.deleteById(id);
		}

}


