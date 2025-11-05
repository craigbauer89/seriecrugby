package com.epicode.progettofinaleepicode.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import com.epicode.progettofinaleepicode.entity.Championship;
import com.epicode.progettofinaleepicode.entity.Classifica;
import com.epicode.progettofinaleepicode.entity.News;
import com.epicode.progettofinaleepicode.entity.NewsDto;
import com.epicode.progettofinaleepicode.entity.Picture;
import com.epicode.progettofinaleepicode.entity.Squadre;
import com.epicode.progettofinaleepicode.entity.Stadium;
import com.epicode.progettofinaleepicode.entity.StadiumDto;
import com.epicode.progettofinaleepicode.repository.ChampionshipRepository;
import com.epicode.progettofinaleepicode.repository.NewsRepository;
import com.epicode.progettofinaleepicode.repository.PictureRepository;
import com.epicode.progettofinaleepicode.repository.SeasonRepository;
import com.epicode.progettofinaleepicode.repository.SquadreRepository;
import com.epicode.progettofinaleepicode.repository.StadiumRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StadiumService {
	
	private StadiumRepository  stadiumRepository;
	private PictureRepository  pictureRepository;
	private SquadreRepository  squadreRepository;
	
	private ObjectProvider<Stadium> stadiumProvider;
	

	public List<Stadium> getAll() {
		return stadiumRepository.findAll();
	}
	

	public Optional<Stadium> getById(Long id) {
		Optional<Stadium>  cliente = stadiumRepository.findById(id);
		if (cliente.isPresent()) return cliente;
		
		throw new EntityNotFoundException("Stadium non trovato");
				
	}
	

	public Stadium insert(StadiumDto dto, Long picture_id) {
		
		Picture picture = pictureRepository.findById(picture_id).orElseThrow(() -> new RuntimeException("Picture not found"));

		
		if(stadiumRepository.existsByName(dto.getName())) {
			throw new EntityExistsException("Stadium gia inserito");
		}

		Stadium stadium = stadiumProvider.getObject();
		BeanUtils.copyProperties(dto, stadium);
		
		stadium.setPicture(picture);
		return stadiumRepository.save(stadium);
		
	//	if (squadra.getStadium() != null){
			
	  //      throw new IllegalStateException("Squadra already linked to a Stadium.");

		//}
		
		//else {
	//	stadium.setSquadra(squadra);
	//	squadra.setStadium(stadium);
		//squadreRepository.save(squadra);
		//}
	
		
		//return stadiumRepository.save(stadium);
	    // Perché `squadra.setStadium(stadium)` + `@OneToOne(cascade = CascadeType.ALL)` si occupa di salvare lo stadio

		// return squadreRepository.save(squadra).getStadium(); 
		
	}
	

		public Stadium update(Long id, StadiumDto dto, Long picture_id) {
		

			
			Optional<Stadium> stadiumUpdate = stadiumRepository.findById(id);
			if (!stadiumUpdate.isPresent()) {
				throw new EntityNotFoundException();	
			}
			
			Stadium stadium = stadiumUpdate.get();
			BeanUtils.copyProperties(dto, stadium);
			
			Picture picture = pictureRepository.findById(picture_id)
		            .orElseThrow(() -> new RuntimeException("Picture non trovata"));
	
			 stadium.setPicture(picture);



			    return stadiumRepository.save(stadium);


		
		}
		
		public void cancella(Long id) {
			if (!stadiumRepository.existsById(id)) {
				throw new EntityNotFoundException("Stadium not found or already deleted");
			}
			
			stadiumRepository.findById(id).orElseThrow(() -> new RuntimeException("Stadium not found"));


			stadiumRepository.deleteById(id);
		}


}
