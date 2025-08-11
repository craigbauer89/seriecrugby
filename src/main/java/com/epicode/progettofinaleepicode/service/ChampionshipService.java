package com.epicode.progettofinaleepicode.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import com.epicode.progettofinaleepicode.entity.Championship;
import com.epicode.progettofinaleepicode.entity.ChampionshipDto;
import com.epicode.progettofinaleepicode.entity.Classifica;
import com.epicode.progettofinaleepicode.entity.ClassificaDto;
import com.epicode.progettofinaleepicode.entity.Partite;
import com.epicode.progettofinaleepicode.entity.Season;
import com.epicode.progettofinaleepicode.repository.ChampionshipRepository;
import com.epicode.progettofinaleepicode.repository.ClassificaRepository;
import com.epicode.progettofinaleepicode.repository.PartiteRepository;
import com.epicode.progettofinaleepicode.repository.SeasonRepository;
import com.epicode.progettofinaleepicode.repository.SquadreRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChampionshipService {
	
	private ChampionshipRepository  championshipRepository;
	private SeasonRepository seasonRepository;
	
	private ObjectProvider<Championship> championshipProvider;
	

	public List<Championship> getAll() {
		return championshipRepository.findAllByOrderByNameAsc();
	}
	

	public Optional<Championship> getById(Long id) {
		Optional<Championship>  cliente = championshipRepository.findById(id);
		if (cliente.isPresent()) return cliente;
		
		throw new EntityNotFoundException("Squadra non trovato");
				
	}
	
	public List<Championship> getBySeasonId(Long seasonid) {
		return championshipRepository.findBySeasonId(seasonid);
		
				
	}
	

		public Championship insert(ChampionshipDto dto, Long seasonId) {
		
        Season season = seasonRepository.findById(seasonId).orElseThrow(() -> new RuntimeException("Season not found"));
       
       List<Championship> championships = season.getLeague();
       
       for (Championship champ : championships) {
			
    	   if (champ.getName().equals(dto.getName())) {
				throw new EntityExistsException("Championship gia inserito per questo Season");
			}

		}
        
     
		Championship championship = championshipProvider.getObject();
		BeanUtils.copyProperties(dto, championship);
		if (championship.getSeason() == null) {
		    championship.setSeason(season);
		}
		
		
		
		
		return championshipRepository.save(championship);
		
	}
	

		public Championship update(Long id, ChampionshipDto dto) {
		
		Optional<Championship> championshipUpdate = championshipRepository.findById(id);
		if (!championshipUpdate.isPresent()) {
			throw new EntityNotFoundException();	
		}
		
		Championship championship = championshipUpdate.get();
		BeanUtils.copyProperties(dto, championship);
		
		
		return championshipRepository.save(championship);
		
	}
		
		public void cancella(Long id) {
			if (!championshipRepository.existsById(id)) {
				throw new EntityNotFoundException("Championship not found or already deleted");
			}
			
			Championship championship = championshipRepository.findById(id).orElseThrow(() -> new RuntimeException("Championship not found"));

			List <Classifica> classifiche = championship.getClassifica();
			
			
			if (classifiche != null && !classifiche.isEmpty()){
		
		        throw new IllegalStateException("Cannot delete the championship as it is already linked to a Clasifica.");

			}
			
			championshipRepository.deleteById(id);
		}

//		public Championship rimouvereChampionshipSeason(Long championship_id, Long season_id) {
//			
//			
//			Championship championship = championshipRepository.findById(championship_id).orElseThrow(() -> new RuntimeException("Championship not found"));
//
//			Season season = seasonRepository.findById(season_id).orElseThrow(() -> new RuntimeException("Season not found"));
//
//			championship.getSeason().remove(season);
//			season.getLeague().remove(championship);
//			
//			seasonRepository.save(season);
//			
//			return championshipRepository.save(championship);
//		}
//		
//	public Championship addChampionshipSeason(Long championship_id, Long season_id) {
//			
//			
//			Championship championship = championshipRepository.findById(championship_id).orElseThrow(() -> new RuntimeException("Championship not found"));
//
//			Season season = seasonRepository.findById(season_id).orElseThrow(() -> new RuntimeException("Season not found"));
//			
//			for (Season champ_season : championship.getSeason()) {
//				
//				if(champ_season.getId() ==  (season.getId())) {
//					throw new EntityExistsException("Season gia inserito");
//				}
//
//			}
//				
//				
//			championship.getSeason().add(season);
//			season.getLeague().add(championship);
//			
//			seasonRepository.save(season);
//			
//			return championshipRepository.save(championship);
//		}



}
