package com.epicode.progettofinaleepicode.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import com.epicode.progettofinaleepicode.entity.Championship;
import com.epicode.progettofinaleepicode.entity.Classifica;
import com.epicode.progettofinaleepicode.entity.ClassificaDto;
import com.epicode.progettofinaleepicode.entity.Participation;
import com.epicode.progettofinaleepicode.entity.Partite;
import com.epicode.progettofinaleepicode.entity.Season;
import com.epicode.progettofinaleepicode.entity.Squadre;
import com.epicode.progettofinaleepicode.entity.SquadreDto;
import com.epicode.progettofinaleepicode.repository.ChampionshipRepository;
import com.epicode.progettofinaleepicode.repository.ClassificaRepository;
import com.epicode.progettofinaleepicode.repository.ParticipationRepository;
import com.epicode.progettofinaleepicode.repository.PartiteRepository;
import com.epicode.progettofinaleepicode.repository.SquadreRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class ClassificaService {
	
	private ClassificaRepository  classificaRepository;
	private ChampionshipRepository  championshipRepository;
	private SquadreRepository  squadreRepository;
	private ParticipationRepository  participationRepository;
	
	private ObjectProvider<Classifica> classificaProvider;
	

	public List<Classifica> getAll() {
		return classificaRepository.findAllByOrderByNameAsc();
	}
	
	public List<Classifica> getByChampionshipId(Long seasonid) {
		return classificaRepository.findByChampionshipId(seasonid);
		
	}
	
	public List<Classifica> getBySquadraId(Long squadraid, String anno) {
		return classificaRepository.findBySquadraIdAndSeasonYear(squadraid, anno);
		
	}
	

	public Optional<Classifica> getById(Long id) {
		Optional<Classifica>  cliente = classificaRepository.findById(id);
		if (cliente.isPresent()) return cliente;
		
		throw new EntityNotFoundException("Squadra non trovato");
				
	}
	

		public Classifica insert(ClassificaDto dto, Long championship_id) {
		
		
		Championship championship = championshipRepository.findById(championship_id).orElseThrow(() -> new RuntimeException("Championship not found"));
		
		List<Classifica> classifiche = championship.getClassifica();
	       
	       for (Classifica classe : classifiche) {
				
	    	   if (classe.getName().equals(dto.getName())) {
					throw new EntityExistsException("Classifica gia inserito per questo Championship");
				}

			}
	        
		
		
		Classifica classifica = classificaProvider.getObject();
		BeanUtils.copyProperties(dto, classifica);
		
		classifica.setChampionship(championship);
		
		return classificaRepository.save(classifica);
		
	}
		public Classifica insertSquadra(Long classifica_id, Long squadra_id) {
			

			
			Classifica classifica = classificaRepository.findById(classifica_id).orElseThrow(() -> new RuntimeException("Classifica not found"));
			
			
			Squadre squadra = squadreRepository.findById(squadra_id).orElseThrow(() -> new RuntimeException("Squadra not found"));
			

			 if (classifica.getSquadre().contains(squadra)) {
			        throw new EntityExistsException("Squadra già presente nella classifica");
			    }
			
		//	Participation participazione = classifica.getParticipationPerSquadra(squadra);
//			 if (squadra.getParticipation() == null) {
//	 			    squadra.setParticipation(new ArrayList<>());
//	 			}
//				squadra.getParticipation().add(participazione);
//				classifica.getSquadre().add(squadra);
//				squadra.getClassifica().add(classifica);
			
			
			Participation participation = new Participation();
			 if (squadra.getParticipation() == null) {
	 			    squadra.setParticipation(new ArrayList<>());
	 			}
			squadra.getParticipation().add(participation);
			participation.setSquadra(squadra);			 
			if (classifica.getParticipation() == null) {
				 classifica.setParticipation(new ArrayList<>());
	 			}
			classifica.getParticipation().add(participation);	
			participation.setClassifica(classifica);
		
			classifica.getSquadre().add(squadra);
			squadra.getClassifica().add(classifica);
			
			squadreRepository.save(squadra);
			
			return classificaRepository.save(classifica);
			
		}
			
		public Classifica cancellaSquadre(Long classifica_id, Long squadra_id) {
			
			Classifica classifica = classificaRepository.findById(classifica_id).orElseThrow(() -> new RuntimeException("Classifica not found"));
			
			Squadre squadra = squadreRepository.findById(squadra_id).orElseThrow(() -> new RuntimeException("Squadra not found"));
			
			Participation participation = participationRepository.findBySquadraIdAndClassificaId(squadra_id, classifica_id);
			

			
			classifica.getParticipation().remove(participation);
			squadra.getParticipation().remove(participation);
			
			participation.setSquadra(null);
			participation.setClassifica(null);

			participationRepository.save(participation);

			
			classifica.getSquadre().remove(squadra);
			squadra.getClassifica().remove(classifica);
			
			squadreRepository.save(squadra);
			
			return classificaRepository.save(classifica);
			
		}
		
		

		public Classifica update(Long id, ClassificaDto dto, Long championship_id) {
		
		Optional<Classifica> squadraUpdate = classificaRepository.findById(id);
		if (!squadraUpdate.isPresent()) {
			throw new EntityNotFoundException();	
		}
		
		Championship championship = championshipRepository.findById(championship_id).orElseThrow(() ->  new RuntimeException("Championship not found"));
		
		Classifica classifica = squadraUpdate.get();
		BeanUtils.copyProperties(dto, classifica);
		
		classifica.setChampionship(championship);
		
		return classificaRepository.save(classifica);
		
	}
		
		public void cancella(Long id) {
			if (!classificaRepository.existsById(id)) {
				throw new EntityNotFoundException("Classifica does not exist of already deleted");
			}
			
			Classifica classifica = classificaRepository.findById(id).orElseThrow(() -> new RuntimeException("Classifica not found"));
			
			List <Squadre> squadre = classifica.getSquadre();
			
			
			if (squadre != null && !squadre.isEmpty()){

		        throw new IllegalStateException("Cannot delete the classifica as it is already linked to a Squadra.");

			}
			
			
			classificaRepository.deleteById(id);
		}


}
