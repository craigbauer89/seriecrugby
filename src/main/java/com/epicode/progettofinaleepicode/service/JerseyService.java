package com.epicode.progettofinaleepicode.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import com.epicode.progettofinaleepicode.entity.Jersey;
import com.epicode.progettofinaleepicode.entity.JerseyDto;
import com.epicode.progettofinaleepicode.entity.Partite;
import com.epicode.progettofinaleepicode.entity.PartiteDto;
import com.epicode.progettofinaleepicode.repository.JerseyRepository;
import com.epicode.progettofinaleepicode.repository.PartiteRepository;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
//@Validated
public class JerseyService {

	private JerseyRepository jerseyRepository;
	
	private ObjectProvider<Jersey> jerseyProvider;
	
	private ObjectProvider<JerseyDto> jerseyDtoProvider;

	public List<Jersey> getAll() {
		return jerseyRepository.findAll();
	}
	
	public Optional<Jersey> getById(Long id) {
		Optional<Jersey>  jersey = jerseyRepository.findById(id);
		if (jersey.isPresent()) return jersey;
		
		throw new EntityNotFoundException("Jersey non trovato");
				
	}
	
	public Jersey getById2(Long id) {
		if(jerseyRepository.existsById(id)) {
			return jerseyRepository.findById(id).get();
		}
		throw new EntityNotFoundException("Jersey non trovato");
	}
	

	public Jersey insert(JerseyDto dto) {
//		if(jerseyRepository.existsByColor(dto.getColor())) {
//			throw new EntityExistsException("Jersey gia inserito");
//		}
		
		Jersey jersey = jerseyProvider.getObject();
		BeanUtils.copyProperties(dto, jersey);
		
		return jerseyRepository.save(jersey);
		
	}
	

		public Jersey update(Long id, JerseyDto dto) {
		Optional<Jersey> jerseyUpdate = jerseyRepository.findById(id);
		if (!jerseyUpdate.isPresent()) {
			throw new EntityNotFoundException();	
		}
		
		Jersey jersey = jerseyUpdate.get();
		BeanUtils.copyProperties(dto, jersey);
		
		return jerseyRepository.save(jersey);
		
	}
		
		public void cancella(Long id) {
			if (!jerseyRepository.existsById(id)) {
				throw new EntityNotFoundException("Partite not trovato");
			}
			
			jerseyRepository.deleteById(id);
		}

}


