package com.epicode.progettofinaleepicode.service;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import com.epicode.progettofinaleepicode.entity.Participation;
import com.epicode.progettofinaleepicode.entity.ParticipationDto;
import com.epicode.progettofinaleepicode.repository.ParticipationRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ParticipationService {

	private ParticipationRepository  participationRepository;
	private ObjectProvider<Participation> participationProvider;
	

	public List<Participation> getAllByClassifica(Long id) {
		return participationRepository.findByClassificaId(id);
	}
	
	
	public List<Participation> getAll() {
		return participationRepository.findAll();
	}

	public Optional<Participation> getById(Long id) {
		Optional<Participation>  cliente = participationRepository.findById(id);
		if (cliente.isPresent()) return cliente;
		
		throw new EntityNotFoundException("Participation non trovato");
				
	}
	

	public Participation insert(ParticipationDto dto) {
	
		Participation participation = participationProvider.getObject();
		BeanUtils.copyProperties(dto, participation);
		
		return participationRepository.save(participation);
		
	}
	


	public Participation update(Long id, ParticipationDto dto) {
				
	Optional<Participation> participationUpdate = participationRepository.findById(id);
	if (!participationUpdate.isPresent()) {
		throw new EntityNotFoundException();	
	}
	
	Participation participation = participationUpdate.get();
	BeanUtils.copyProperties(dto, participation);

	return participationRepository.save(participation);
		
	}
		
	public void cancella(Long id) {
		if (!participationRepository.existsById(id)) {
			throw new EntityNotFoundException("Participation not found or already deleted");
		}
		
		participationRepository.deleteById(id);
	}

}

