package com.epicode.progettofinaleepicode.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.epicode.progettofinaleepicode.entity.Classifica;
import com.epicode.progettofinaleepicode.entity.Jersey;
import com.epicode.progettofinaleepicode.entity.Partite;
import com.epicode.progettofinaleepicode.entity.PartiteDto;
import com.epicode.progettofinaleepicode.entity.Squadre;
import com.epicode.progettofinaleepicode.entity.SquadreDto;
import com.epicode.progettofinaleepicode.repository.ClassificaRepository;
import com.epicode.progettofinaleepicode.repository.PartiteRepository;
import com.epicode.progettofinaleepicode.repository.SquadreRepository;

import lombok.AllArgsConstructor;




@Service
@AllArgsConstructor
//@Validated
public class PartiteService {
	


	private PartiteRepository  partiteRepository;
	
	private ClassificaRepository classificaRepository;
	
	private SquadreRepository  squadreRepository;
	
	private ObjectProvider<Partite> partiteProvider;
	

	public List<Partite> getAll() {
		return partiteRepository.findAll();
	}
	
	public List<Partite> getAllByYear(Integer year) {
		return partiteRepository.getAllByYear(year);
	}
	
	public List<Partite> getAllBySquadra(Long squadra_id) {
		return partiteRepository.getAllBySquadra(squadra_id);
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
		
		// undo the below if not needed
	    Squadre squadra1 = squadreRepository.findById(dto.getSquadra1_id())
                .orElseThrow(() -> new RuntimeException("Squadra 1 non trovata"));
	    Squadre squadra2 = squadreRepository.findById(dto.getSquadra2_id())
                .orElseThrow(() -> new RuntimeException("Squadra 2 non trovata"));
	    
	    Classifica classifica = classificaRepository.findById(dto.getClassifica_id())
                .orElseThrow(() -> new RuntimeException("Classifica non trovata"));

	    partita.setSquadra1(squadra1);
	    partita.setSquadra2(squadra2);
	    
	    partita.setClassifica(classifica);	 
	    
	    
	    squadra1.getHomeGames().add(partita);
	    squadra2.getAwaygames().add(partita);
	    
	    classifica.getPartite().add(partita);
		
		squadreRepository.save(squadra1);
		squadreRepository.save(squadra2);
		
		classificaRepository.save(classifica);
	
	    //_______________________________
	    
		updateSquadra(dto);

		return partiteRepository.save(partita);
		
	}
	
		//	public Partite update(Long id, @Valid PartiteDto dto) {
	@SuppressWarnings("null")
	public Partite update(Long id, PartiteDto dto) {
		Optional<Partite> partitaUpdate = partiteRepository.findById(id);
		if (!partitaUpdate.isPresent()) {
			throw new EntityNotFoundException();	
		}
		
		resetSquadra(id);
		updateSquadra(dto);
		
		Partite partita = partitaUpdate.get();
			
		BeanUtils.copyProperties(dto, partita);
		// undo the below if not needed
		   Squadre squadra1 = squadreRepository.findById(dto.getSquadra1_id())
	                .orElseThrow(() -> new RuntimeException("Squadra 1 non trovata"));
		    Squadre squadra2 = squadreRepository.findById(dto.getSquadra2_id())
	                .orElseThrow(() -> new RuntimeException("Squadra 2 non trovata"));

		    partita.setSquadra1(squadra1);
		    partita.setSquadra2(squadra2);
		    
		    squadra1.getHomeGames().add(partita);
		    squadra2.getAwaygames().add(partita);
			
			squadreRepository.save(squadra1);
			squadreRepository.save(squadra2);
		
		    //_______________________________
		 
		    
		  
		    
		return partiteRepository.save(partita);
		
	}
		
	public void cancella(Long id) {
		if (!partiteRepository.existsById(id)) {
			throw new EntityNotFoundException("Partite not trovato");
		}
		
		resetSquadra(id);
		
		Partite partita = partiteRepository.findById(id).orElseThrow(() -> new RuntimeException("Partita not found"));

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

	
	public void updateSquadra(PartiteDto dto) {
		
		//go back and undo the below if does not work
		//Squadre squadra1 = getSquadre(dto.getSquadra1().getId() );
		//Squadre squadra2 = getSquadre(dto.getSquadra2().getId());
		Squadre squadra1 = getSquadre(dto.getSquadra1_id() );
		Squadre squadra2 = getSquadre(dto.getSquadra2_id() );
		
		int resultDto = dto.getPuntisquadra1()- dto.getPuntisquadra2();
		
		if (resultDto > 1) {
			squadra1.setPunti(squadra1.getPunti()+4);
			squadra2.setSconfitte(squadra2.getSconfitte()+1);
			squadra1.setVittorie(squadra1.getVittorie()+1);
		}
		else if (resultDto < 1) {
			squadra1.setSconfitte(squadra1.getSconfitte()+1);
			squadra2.setVittorie(squadra2.getVittorie()+1);
			squadra2.setPunti(squadra2.getPunti()+4);
		}
		else {
			squadra1.setPunti(squadra1.getPunti()+2);
			squadra1.setPareggi(squadra1.getPareggi()+1);
			squadra2.setPunti(squadra2.getPunti()+2);
			squadra2.setPareggi(squadra2.getPareggi()+1);
			}
		
		squadra1.setMeteFatti(squadra1.getMeteFatti() + dto.getMeteSquadra1());
		squadra1.setMeteSubiti(squadra1.getMeteSubiti()+ dto.getMeteSquadra2());
		squadra1.setPuntiSubiti(squadra1.getPuntiSubiti()+dto.getPuntisquadra2());		
		squadra1.setPuntiFatti(squadra1.getPuntiFatti()+dto.getPuntisquadra1());;	
	
		squadra2.setMeteFatti(squadra2.getMeteFatti() + dto.getMeteSquadra2());
		squadra2.setMeteSubiti(squadra2.getMeteSubiti()+ dto.getMeteSquadra1());
		squadra2.setPuntiSubiti(squadra2.getPuntiSubiti()+dto.getPuntisquadra1());		
		squadra2.setPuntiFatti(squadra2.getPuntiFatti()+dto.getPuntisquadra2());;	
	
		squadra1.setGiocate(squadra1.getGiocate()+1);
		squadra2.setGiocate(squadra2.getGiocate()+1);
		
		/// does this get a new ID???
		squadreRepository.save(squadra1);
		squadreRepository.save(squadra2);
		
	}
	
	public void resetSquadra(Long id) {
		
		Partite partita = partiteRepository.findById(id)
	            .orElseThrow(EntityNotFoundException::new); 
				
		Squadre squadra1 = getSquadre(partita.getSquadra1().getId());
		Squadre squadra2 = getSquadre(partita.getSquadra2().getId());
		
		int result = partita.getPuntisquadra1()- partita.getPuntisquadra2();
		
		if (result > 1) {
			squadra1.setPunti(squadra1.getPunti()-4);
			squadra2.setSconfitte(squadra2.getSconfitte()-1);
			squadra1.setVittorie(squadra1.getVittorie()-1);
		}
		else if (result < 1) {
			squadra1.setSconfitte(squadra1.getSconfitte()-1);
			squadra2.setVittorie(squadra2.getVittorie()-1);
			squadra2.setPunti(squadra2.getPunti()-4);
		}
		else {
			squadra1.setPunti(squadra1.getPunti()-2);
			squadra1.setPareggi(squadra1.getPareggi()-1);
			squadra2.setPunti(squadra2.getPunti()-2);
			squadra2.setPareggi(squadra2.getPareggi()-1);
		}
		
		squadra1.setMeteFatti(squadra1.getMeteFatti() - partita.getMeteSquadra1());
		squadra1.setMeteSubiti(squadra1.getMeteSubiti()- partita.getMeteSquadra2());
		squadra1.setPuntiSubiti(squadra1.getPuntiSubiti()-partita.getPuntisquadra2());		
		squadra1.setPuntiFatti(squadra1.getPuntiFatti()-partita.getPuntisquadra1());;
		
		squadra2.setMeteFatti(squadra2.getMeteFatti() - partita.getMeteSquadra2());
		squadra2.setMeteSubiti(squadra2.getMeteSubiti()- partita.getMeteSquadra1());
		squadra2.setPuntiSubiti(squadra2.getPuntiSubiti()-partita.getPuntisquadra1());		
		squadra2.setPuntiFatti(squadra2.getPuntiFatti()-partita.getPuntisquadra2());;
		
		squadra1.setGiocate(squadra1.getGiocate()-1);
		squadra2.setGiocate(squadra2.getGiocate()-1);
		
		squadreRepository.save(squadra1);
		squadreRepository.save(squadra2);
	}

}


