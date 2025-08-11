package com.epicode.progettofinaleepicode.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.epicode.progettofinaleepicode.entity.Championship;
import com.epicode.progettofinaleepicode.entity.Classifica;
import com.epicode.progettofinaleepicode.entity.Jersey;
import com.epicode.progettofinaleepicode.entity.Partite;
import com.epicode.progettofinaleepicode.entity.Season;
import com.epicode.progettofinaleepicode.entity.Squadre;
import com.epicode.progettofinaleepicode.entity.SquadreDto;
import com.epicode.progettofinaleepicode.repository.JerseyRepository;
import com.epicode.progettofinaleepicode.repository.SquadreRepository;

import lombok.AllArgsConstructor;



@Service
@AllArgsConstructor
//@Validated
public class SquadreService {

	private SquadreRepository  squadreRepository;
	
	private JerseyRepository  jerseyRepository;
	
	private ObjectProvider<Squadre> squadreProvider;
	

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
	
	public List<Squadre> getByClassificaId(Long seasonid) {
		return squadreRepository.getByClassifica(seasonid);
		
				
	}
	
	public Squadre getById2(Long id) {
		if(squadreRepository.existsById(id)) {
			return squadreRepository.findById(id).get();
		}
		throw new EntityNotFoundException("Squadra non trovato");
	}
	
	public List<Partite> getHomePartiteById(Long id) {
		if(squadreRepository.existsById(id)) {
			return (List<Partite>) squadreRepository.findById(id).get().getHomeGames();
		}
		throw new EntityNotFoundException("Squadra non trovato");
	}
	
	
	public List<Partite> getAwayPartiteById(Long id) {
		if(squadreRepository.existsById(id)) {
			return (List<Partite>) squadreRepository.findById(id).get().getAwaygames();
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
	
		
		
		public Squadre insertJersey(Long id, Long jersery_id) {
			
			
			Squadre squadra = squadreRepository.findById(id).orElseThrow(() -> new RuntimeException("Squadra not found"));
			
			Jersey jersey = jerseyRepository.findById(jersery_id).orElseThrow(() -> new RuntimeException("Jersey not found"));

			squadra.setJersey(jersey);
			
			return squadreRepository.save(squadra);
			
		}
		
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
		
		
			Squadre squadra = squadreRepository.findById(id).orElseThrow(() -> new RuntimeException("Squadra not found"));

			List <Partite> partiteHome = squadra.getHomeGames();
			List <Partite> partiteAway = squadra.getAwaygames();
			
			
			if (partiteHome != null && partiteAway != null &&  !partiteHome.isEmpty() && !partiteAway.isEmpty()){
		
		        throw new IllegalStateException("Cannot delete the squadre as it is already linked to a Partita.");

			}
			
		
			
			squadreRepository.deleteById(id);
		}
		
		public void cancellaJersey(Long id) {
			
			  Squadre squadra = squadreRepository.findById(id).orElseThrow(() -> new RuntimeException("Squadra not found"));

			
			    if (squadra.getJersey() != null) {
			        Jersey jersey = jerseyRepository.findById(squadra.getJersey().getId())
			                .orElseThrow(() -> new RuntimeException("Jersey not found"));
			        
			   
			        squadra.setJersey(null);  

			        squadreRepository.save(squadra);
			    } else {
			        throw new RuntimeException("No jersey associated with this Squadra");
			    }
		
			
			
		}

}


