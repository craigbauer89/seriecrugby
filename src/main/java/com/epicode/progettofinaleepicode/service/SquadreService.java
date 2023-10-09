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

import com.epicode.progettofinaleepicode.entity.Squadre;
import com.epicode.progettofinaleepicode.entity.SquadreDto;
import com.epicode.progettofinaleepicode.repository.SquadreRepository;

import lombok.AllArgsConstructor;



@Service
@AllArgsConstructor
//@Validated
public class SquadreService {

	private SquadreRepository  squadreRepository;
	
	private ObjectProvider<Squadre> squadreProvider;
	
	private ObjectProvider<SquadreDto> squadreDtoProvider;


	public List<Squadre> getAll() {
		return squadreRepository.getAllOrdered();
	}

	public List<Squadre> getAllAlpha() {
		return squadreRepository.getAllOrderedAlpha();
	}
	public Optional<Squadre> getById(Long id) {
		Optional<Squadre>  cliente = squadreRepository.findById(id);
		if (cliente.isPresent()) return cliente;
		
		throw new EntityNotFoundException("Squadra non trovato");
				
	}
	
	public Squadre getById2(Long id) {
		if(squadreRepository.existsById(id)) {
			return squadreRepository.findById(id).get();
		}
		throw new EntityNotFoundException("Squadra non trovato");
	}
	
//	public Squadre insert(@Valid SquadreDto dto) {
		public Squadre insert(SquadreDto dto) {
		if(squadreRepository.existsByNome(dto.getNome())) {
			throw new EntityExistsException("Squadra gia inserito");
		}
		
		Squadre squadra = squadreProvider.getObject();
		BeanUtils.copyProperties(dto, squadra);
		
		return squadreRepository.save(squadra);
		
	}
	
//	public Squadre update(Long id, @Valid SquadreDto dto) {
		public Squadre update(Long id, SquadreDto dto) {
		
		Optional<Squadre> squadraUpdate = squadreRepository.findById(id);
		if (!squadraUpdate.isPresent()) {
			throw new EntityNotFoundException();	
		}
		
		Squadre squadra = squadraUpdate.get();
		BeanUtils.copyProperties(dto, squadra);
		
		return squadreRepository.save(squadra);
		
	}
		
		public void cancella(Long id) {
			if (!squadreRepository.existsById(id)) {
				throw new EntityNotFoundException("Squadra not trovato");
			}
			
			squadreRepository.deleteById(id);
		}

}


