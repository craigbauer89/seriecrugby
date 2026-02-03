package com.epicode.progettofinaleepicode.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import com.epicode.progettofinaleepicode.entity.Channel;
import com.epicode.progettofinaleepicode.entity.Classifica;
import com.epicode.progettofinaleepicode.entity.Jersey;
import com.epicode.progettofinaleepicode.entity.Participation;
import com.epicode.progettofinaleepicode.entity.Partite;
import com.epicode.progettofinaleepicode.entity.PartiteDto;
import com.epicode.progettofinaleepicode.entity.Picture;
import com.epicode.progettofinaleepicode.entity.Squadre;
import com.epicode.progettofinaleepicode.entity.SquadreDto;
import com.epicode.progettofinaleepicode.entity.Stadium;
import com.epicode.progettofinaleepicode.repository.ChannelRepository;
import com.epicode.progettofinaleepicode.repository.ClassificaRepository;
import com.epicode.progettofinaleepicode.repository.ParticipationRepository;
import com.epicode.progettofinaleepicode.repository.PartiteRepository;
import com.epicode.progettofinaleepicode.repository.SquadreRepository;
import com.epicode.progettofinaleepicode.repository.StadiumRepository;

import lombok.AllArgsConstructor;




@Service
@AllArgsConstructor
//@Validated
public class PartiteService {
	
	private ChannelRepository channelRepository;
	private StadiumRepository	stadiumRepository;

	private PartiteRepository  partiteRepository;
	
	private ClassificaRepository classificaRepository;
	
	private SquadreRepository  squadreRepository;
	
	private ObjectProvider<Partite> partiteProvider;
	private ParticipationRepository participationRepository;
	

	public List<Partite> getAll() {
		return partiteRepository.findAll();
	}
	
	public List<Partite> getAllByYear(Integer year) {
		return partiteRepository.getAllByYear(year);
	}
	
	public List<Partite> getAllBySeason(LocalDate start, LocalDate end) {
		return partiteRepository.findBySeason(start, end);
	}
	
	public List<Partite> getAllByChampFixtures(Long championship_id) {
		return partiteRepository.getChampFixtures(championship_id);
	}
	
	public List<Partite> getAllByChampResults(Long championship_id) {
		return partiteRepository.getChampResults(championship_id);
	}
	
	public List<Partite> getAllBySquadra(Long squadra_id) {
		return partiteRepository.getAllBySquadra(squadra_id);
	}
	public List<Partite> getAllByClassifica(Long classifica_id) {
		return partiteRepository.getAllByClassifica(classifica_id);
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
	public Partite insert(PartiteDto dto, Long channel_id,Long stadium_id) {
		//		if(partiteRepository.existsByDate(dto.getDate())) {
		//			throw new EntityExistsException("Partita gia inserito");
		//		}
		
		Stadium	stadium	= stadiumRepository.findById(stadium_id).orElseThrow(() -> new RuntimeException("Stadium not found"));

		LocalDate today = LocalDate.now();

		if (dto.getDate().isAfter(today)) {
		    
			 if (dto.getMeteSquadra1()> 0 || dto.getMeteSquadra2()> 0 || dto.getPuntisquadra1() > 0 || dto.getPuntisquadra1() > 0 ) {
			        throw new ResponseStatusException(
			            HttpStatus.BAD_REQUEST, 
			            "Partite nel futuro nel possono avere punti"
			        );
			    }
			
		}
		
		
		Partite partita = partiteProvider.getObject();
		BeanUtils.copyProperties(dto, partita);
		
		// undo the below if not needed
	    Squadre squadra1 = squadreRepository.findById(dto.getSquadra1_id())
                .orElseThrow(() -> new RuntimeException("Squadra 1 non trovata"));
	    Squadre squadra2 = squadreRepository.findById(dto.getSquadra2_id())
                .orElseThrow(() -> new RuntimeException("Squadra 2 non trovata"));
	    
	    if (squadra1.getId().equals(squadra2.getId())) {
	        throw new ResponseStatusException(
	            HttpStatus.BAD_REQUEST, 
	            "Squadra 1 e Squadra 2 non possono essere uguali"
	        );
	    }
	    Classifica classifica = classificaRepository.findById(dto.getClassifica_id())
                .orElseThrow(() -> new RuntimeException("Classifica non trovata"));

	    partita.setSquadra1(squadra1);
	    partita.setSquadra2(squadra2);
	    
		Channel channel = channelRepository.findById(channel_id)
	            .orElseThrow(() -> new RuntimeException("Channel non trovata"));

		partita.setChannel(channel);
	    
	    partita.setClassifica(classifica);	
	    partita.setStadium(stadium);
	    
	    
	    squadra1.getHomeGames().add(partita);
	    squadra2.getAwaygames().add(partita);
	    
	    classifica.getPartite().add(partita);
		
		squadreRepository.save(squadra1);
		squadreRepository.save(squadra2);
		
		classificaRepository.save(classifica);
	
	    //_______________________________
	    
		if (dto.getPlayed()) {
			updateSquadra(dto,classifica.getId());
		
		}
		
		
		return partiteRepository.save(partita);
		
	}
	
		//	public Partite update(Long id, @Valid PartiteDto dto) {
	@SuppressWarnings("null")
	public Partite update(Long id, PartiteDto dto,Long channel_id,Long stadium_id) {
		
		
		Channel channel = channelRepository.findById(channel_id)
	            .orElseThrow(() -> new RuntimeException("Channel non trovata"));
		
		Stadium	stadium	= stadiumRepository.findById(stadium_id).orElseThrow(() -> new RuntimeException("Stadium not found"));

		
		Optional<Partite> partitaUpdate = partiteRepository.findById(id);
		if (!partitaUpdate.isPresent()) {
			throw new EntityNotFoundException();	
		}
		
		Classifica classifica = classificaRepository.findById(dto.getClassifica_id())
                .orElseThrow(() -> new RuntimeException("Classifica non trovata: id=" + dto.getClassifica_id()));
		
		// check if the game being modified is a game in the past, if yes reset it
		if (partitaUpdate.get().getPlayed()) {
			resetSquadra(id,classifica.getId());
			
			// check if the modified game is a game in the past, if yes, update it
			if (dto.getPlayed()) {
				updateSquadra(dto,classifica.getId());
			
			}
		}
		//if future game, 

		else {
			// check if the modified game is a game in the past, if yes, update it
						if (dto.getPlayed()) {
							updateSquadra(dto,classifica.getId());
						
						}
		}
		
		
		
				
		
		Partite partita = partitaUpdate.get();
			
		BeanUtils.copyProperties(dto, partita);
		// undo the below if not needed
		   Squadre squadra1 = squadreRepository.findById(dto.getSquadra1_id())
	                .orElseThrow(() -> new RuntimeException("Squadra 1 non trovata"));
		    Squadre squadra2 = squadreRepository.findById(dto.getSquadra2_id())
	                .orElseThrow(() -> new RuntimeException("Squadra 2 non trovata"));
		    
		    
		    partita.setChannel(channel);
		    partita.setStadium(stadium);

		    partita.setSquadra1(squadra1);
		    partita.setSquadra2(squadra2);
		    
		    squadra1.getHomeGames().add(partita);
		    squadra2.getAwaygames().add(partita);
			
			squadreRepository.save(squadra1);
			squadreRepository.save(squadra2);
		    
		  
		    
		return partiteRepository.save(partita);
		
	}
		
	public void cancella(Long id) {
		if (!partiteRepository.existsById(id)) {
			throw new EntityNotFoundException("Partite not trovato");
		}
		Partite partita = partiteRepository.findById(id).orElseThrow(() -> new RuntimeException("Partita not found"));
		
		Long classifica_id = partita.getClassifica_id();
		
		// check if the game being modified is a game in the past, if yes reset it
		if (partita.getPlayed()) {
			resetSquadra(id,classifica_id);
			
		}
		

		   Squadre squadra1 = squadreRepository.findById(partita.getSquadra1().getId())
	                .orElseThrow(() -> new RuntimeException("Squadra 1 non trovata"));
		    Squadre squadra2 = squadreRepository.findById(partita.getSquadra2().getId())
	                .orElseThrow(() -> new RuntimeException("Squadra 2 non trovata"));

		    squadra1.getHomeGames().remove(partita);
		    squadra2.getAwaygames().remove(partita);
			
			squadreRepository.save(squadra1);
			squadreRepository.save(squadra2);
		
		partiteRepository.deleteById(id);
		
	}
	
	public Squadre getSquadre(Long id) {
	    return squadreRepository.findById(id)
	            .orElseThrow(EntityNotFoundException::new); // Throw exception if not found
	}

	
	public void updateSquadra(PartiteDto dto, Long classifica_id ) {
		
		//go back and undo the below if does not work
		//Squadre squadra1 = getSquadre(dto.getSquadra1().getId() );
		//Squadre squadra2 = getSquadre(dto.getSquadra2().getId());
		Squadre squadra1 = getSquadre(dto.getSquadra1_id() );
		Squadre squadra2 = getSquadre(dto.getSquadra2_id() );
		
		
		Participation squadra1Participation = participationRepository.findBySquadraIdAndClassificaId(dto.getSquadra1_id(), classifica_id);
		Participation squadra2Participation = participationRepository.findBySquadraIdAndClassificaId(dto.getSquadra2_id(), classifica_id);
		
		
		int puntisquadra1Count = squadra1.getPunti();
		int puntisquadra2Count = squadra2.getPunti();
		
		int resultDto = dto.getPuntisquadra1()- dto.getPuntisquadra2();
		
		if (resultDto >= -7 && resultDto < 0)	{
			puntisquadra1Count += 1;
		
		}
		
		if (resultDto <= 7 && resultDto > 0)	{
			puntisquadra2Count +=  1;
	
		}
		
		if (dto.getMeteSquadra1()>= 4) { 
			puntisquadra1Count +=  1;
			
		}
		
		if (dto.getMeteSquadra2()>= 4) { 
			puntisquadra2Count +=  1;
			
		}
		
		if (resultDto > 0) {
			puntisquadra1Count +=  4;
			
			squadra2.setSconfitte(squadra2.getSconfitte()+1);
			squadra1.setVittorie(squadra1.getVittorie()+1);
			
			squadra2Participation.setSconfitte(squadra2Participation.getSconfitte()+1);
			squadra1Participation.setVittorie(squadra1Participation.getVittorie()+1);
		}
		else if (resultDto < 0) {
			squadra1.setSconfitte(squadra1.getSconfitte()+1);
			squadra2.setVittorie(squadra2.getVittorie()+1);
			
			squadra1Participation.setSconfitte(squadra1Participation.getSconfitte()+1);
			squadra2Participation.setVittorie(squadra2Participation.getVittorie()+1);
			
			puntisquadra2Count += 4;
			
		}
		else {
			puntisquadra1Count += 2;
			//squadra1.setPunti(squadra1.getPunti()+2);
			squadra1.setPareggi(squadra1.getPareggi()+1);
			
			squadra1Participation.setPareggi(squadra1Participation.getPareggi()+1);
			
			puntisquadra2Count += 2;
			//squadra2.setPunti(squadra2.getPunti()+2);
			squadra2.setPareggi(squadra2.getPareggi()+1);
			
			squadra2Participation.setPareggi(squadra2Participation.getPareggi()+1);
			
			}
		
		squadra1.setMeteFatti(squadra1.getMeteFatti() + dto.getMeteSquadra1());
		squadra1.setMeteSubiti(squadra1.getMeteSubiti()+ dto.getMeteSquadra2());
		squadra1.setPuntiSubiti(squadra1.getPuntiSubiti()+dto.getPuntisquadra2());		
		squadra1.setPuntiFatti(squadra1.getPuntiFatti()+dto.getPuntisquadra1());;	
		
		squadra1Participation.setMeteFatti(squadra1Participation.getMeteFatti() + dto.getMeteSquadra1());
		squadra1Participation.setMeteSubiti(squadra1Participation.getMeteSubiti()+ dto.getMeteSquadra2());
		squadra1Participation.setPuntiSubiti(squadra1Participation.getPuntiSubiti()+dto.getPuntisquadra2());		
		squadra1Participation.setPuntiFatti(squadra1Participation.getPuntiFatti()+dto.getPuntisquadra1());;	
		
	
		squadra2.setMeteFatti(squadra2.getMeteFatti() + dto.getMeteSquadra2());
		squadra2.setMeteSubiti(squadra2.getMeteSubiti()+ dto.getMeteSquadra1());
		squadra2.setPuntiSubiti(squadra2.getPuntiSubiti()+dto.getPuntisquadra1());		
		squadra2.setPuntiFatti(squadra2.getPuntiFatti()+dto.getPuntisquadra2());;	
		
		squadra2Participation.setMeteFatti(squadra2Participation.getMeteFatti() + dto.getMeteSquadra2());
		squadra2Participation.setMeteSubiti(squadra2Participation.getMeteSubiti()+ dto.getMeteSquadra1());
		squadra2Participation.setPuntiSubiti(squadra2Participation.getPuntiSubiti()+dto.getPuntisquadra1());		
		squadra2Participation.setPuntiFatti(squadra2Participation.getPuntiFatti()+dto.getPuntisquadra2());;		
	
		squadra1.setGiocate(squadra1.getGiocate()+1);
		squadra2.setGiocate(squadra2.getGiocate()+1);
		
		squadra1Participation.setGiocate(squadra1Participation.getGiocate()+1);
		squadra2Participation.setGiocate(squadra2Participation.getGiocate()+1);
		
		squadra1.setPunti(puntisquadra1Count);
		squadra2.setPunti(puntisquadra2Count);
		
		squadra1Participation.setPunti(puntisquadra1Count);
		squadra2Participation.setPunti(puntisquadra2Count);
		
		squadreRepository.save(squadra1);
		squadreRepository.save(squadra2);
		
		participationRepository.save(squadra1Participation);
		participationRepository.save(squadra2Participation);
		
		
	}
	
	public void resetSquadra(Long id,Long classifica_Id) {
		
		Partite partita = partiteRepository.findById(id)
	            .orElseThrow(EntityNotFoundException::new); 
				
		Squadre squadra1 = getSquadre(partita.getSquadra1().getId());
		Squadre squadra2 = getSquadre(partita.getSquadra2().getId());
		
		Participation squadra1Participation = participationRepository.findBySquadraIdAndClassificaId(squadra1.getId(), classifica_Id);
		Participation squadra2Participation = participationRepository.findBySquadraIdAndClassificaId(squadra2.getId(),  classifica_Id);
		
		
		int result = partita.getPuntisquadra1()- partita.getPuntisquadra2();
		
		
		if (result >= -7 && result < 0)	{
			
			squadra1.setPunti(squadra1.getPunti()-1);
			squadra1Participation.setPunti(squadra1Participation.getPunti()-1);
		
		}
		
		if (result <= 7 && result > 0)	{
			
			squadra2.setPunti(squadra2.getPunti()-1);
			squadra2Participation.setPunti(squadra2Participation.getPunti()-1);
	
		}
		
		if (partita.getMeteSquadra1()>= 4) { 
			squadra1.setPunti(squadra1.getPunti()-1);
			squadra1Participation.setPunti(squadra1Participation.getPunti()-1);
			
		}
		
		if (partita.getMeteSquadra2()>= 4) { 
			squadra2.setPunti(squadra2.getPunti()-1);
			squadra2Participation.setPunti(squadra2Participation.getPunti()-1);
			
		}
		
		if (result > 1) {
			squadra1.setPunti(squadra1.getPunti()-4);
			squadra2.setSconfitte(squadra2.getSconfitte()-1);
			squadra1.setVittorie(squadra1.getVittorie()-1);
			
			squadra1Participation.setPunti(squadra1Participation.getPunti()-4);
			squadra2Participation.setSconfitte(squadra2Participation.getSconfitte()-1);
			squadra1Participation.setVittorie(squadra1Participation.getVittorie()-1);
			
			
		}
		else if (result < 1) {
			squadra1.setSconfitte(squadra1.getSconfitte()-1);
			squadra2.setVittorie(squadra2.getVittorie()-1);
			squadra2.setPunti(squadra2.getPunti()-4);
			
			squadra1Participation.setSconfitte(squadra1Participation.getSconfitte()-1);
			squadra2Participation.setVittorie(squadra2Participation.getVittorie()-1);
			squadra2Participation.setPunti(squadra2Participation.getPunti()-4);
		}
		else {
			squadra1.setPunti(squadra1.getPunti()-2);
			squadra1.setPareggi(squadra1.getPareggi()-1);
			squadra2.setPunti(squadra2.getPunti()-2);
			squadra2.setPareggi(squadra2.getPareggi()-1);
			
			squadra1Participation.setPunti(squadra1Participation.getPunti()-2);
			squadra1Participation.setPareggi(squadra1Participation.getPareggi()-1);
			squadra2Participation.setPunti(squadra2Participation.getPunti()-2);
			squadra2Participation.setPareggi(squadra2Participation.getPareggi()-1);
		}
		
		squadra1.setMeteFatti(squadra1.getMeteFatti() - partita.getMeteSquadra1());
		squadra1.setMeteSubiti(squadra1.getMeteSubiti()- partita.getMeteSquadra2());
		squadra1.setPuntiSubiti(squadra1.getPuntiSubiti()-partita.getPuntisquadra2());		
		squadra1.setPuntiFatti(squadra1.getPuntiFatti()-partita.getPuntisquadra1());;
		
		squadra1Participation.setMeteFatti(squadra1Participation.getMeteFatti() - partita.getMeteSquadra1());
		squadra1Participation.setMeteSubiti(squadra1Participation.getMeteSubiti()- partita.getMeteSquadra2());
		squadra1Participation.setPuntiSubiti(squadra1Participation.getPuntiSubiti()-partita.getPuntisquadra2());		
		squadra1Participation.setPuntiFatti(squadra1Participation.getPuntiFatti()-partita.getPuntisquadra1());;
		
		squadra2.setMeteFatti(squadra2.getMeteFatti() - partita.getMeteSquadra2());
		squadra2.setMeteSubiti(squadra2.getMeteSubiti()- partita.getMeteSquadra1());
		squadra2.setPuntiSubiti(squadra2.getPuntiSubiti()-partita.getPuntisquadra1());		
		squadra2.setPuntiFatti(squadra2.getPuntiFatti()-partita.getPuntisquadra2());;
		
		squadra2Participation.setMeteFatti(squadra2Participation.getMeteFatti() - partita.getMeteSquadra2());
		squadra2Participation.setMeteSubiti(squadra2Participation.getMeteSubiti()- partita.getMeteSquadra1());
		squadra2Participation.setPuntiSubiti(squadra2Participation.getPuntiSubiti()-partita.getPuntisquadra1());		
		squadra2Participation.setPuntiFatti(squadra2Participation.getPuntiFatti()-partita.getPuntisquadra2());;
		
		squadra1.setGiocate(squadra1.getGiocate()-1);
		squadra2.setGiocate(squadra2.getGiocate()-1);
		
		squadra1Participation.setGiocate(squadra1Participation.getGiocate()-1);
		squadra2Participation.setGiocate(squadra2Participation.getGiocate()-1);
		
		squadreRepository.save(squadra1);
		squadreRepository.save(squadra2);
		
		participationRepository.save(squadra1Participation);
		participationRepository.save(squadra2Participation);
	}

}


