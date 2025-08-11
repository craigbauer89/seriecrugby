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
import com.epicode.progettofinaleepicode.entity.Partite;
import com.epicode.progettofinaleepicode.entity.Season;
import com.epicode.progettofinaleepicode.entity.SeasonDto;
import com.epicode.progettofinaleepicode.repository.ChampionshipRepository;
import com.epicode.progettofinaleepicode.repository.PartiteRepository;
import com.epicode.progettofinaleepicode.repository.SeasonRepository;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class SeasonService {
	
	private SeasonRepository  seasonRepository;
	private ChampionshipRepository  championshipRepository;
	
	private ObjectProvider<Season> seasonProvider;
	

	public List<Season> getAll() {
		return seasonRepository.findAllByOrderByYearDesc();
	}
	

//	public Optional<Season> getById(Long id) {
//		Optional<Season>  cliente = seasonRepository.findById(id);
//		if (cliente.isPresent()) return cliente;
//		
//		throw new EntityNotFoundException("Season non trovato");
//				
//	}
	
	public Season getById(Long id) {
	    return seasonRepository.findById(id)
	        .orElseThrow(() -> new EntityNotFoundException("Season non trovato"));
	}
	

		public Season insert(SeasonDto dto) {
		if(seasonRepository.existsByYear(dto.getYear())) {
			throw new EntityExistsException("Season gia inserito");
		}
		
		Season season = seasonProvider.getObject();
		BeanUtils.copyProperties(dto, season);
		
		return seasonRepository.save(season);
		
	}
	

		public Season update(Long id, SeasonDto dto) {
		
		Optional<Season> seasonUpdate = seasonRepository.findById(id);
		if (!seasonUpdate.isPresent()) {
			throw new EntityNotFoundException();	
		}
		
		Season season = seasonUpdate.get();
		BeanUtils.copyProperties(dto, season);
		
		return seasonRepository.save(season);
		
	}
		
		public void cancella(Long id) {
			
			Season season = seasonRepository.findById(id).orElseThrow(() -> new RuntimeException("Season not found"));
			
			List <Championship> champsionships = season.getLeague();
			
			
			if (champsionships != null && !champsionships.isEmpty()){

		        throw new IllegalStateException("Cannot delete the season as it is already linked to a Championship.");

			}
			
			seasonRepository.deleteById(id);
		}


}
