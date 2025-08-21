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
import com.epicode.progettofinaleepicode.entity.Player;
import com.epicode.progettofinaleepicode.entity.PlayerDTO;
import com.epicode.progettofinaleepicode.repository.ChampionshipRepository;
import com.epicode.progettofinaleepicode.repository.PlayerRepository;
import com.epicode.progettofinaleepicode.repository.SeasonRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PlayerService {
	
	private PlayerRepository  playerRepository;
	
	private ObjectProvider<Player> playerProvider;
	

	public List<Player> getAll() {
		return playerRepository.findAll();
	}
	

	public Optional<Player> getById(Long id) {
		Optional<Player>  cliente = playerRepository.findById(id);
		if (cliente.isPresent()) return cliente;
		
		throw new EntityNotFoundException("Player non trovato");
				
	}
	

	public Player insert(PlayerDTO dto) {
		if(playerRepository.existsByName(dto.getName())) {
			throw new EntityExistsException("Player gia inserito");
		}

		Player player = playerProvider.getObject();
		BeanUtils.copyProperties(dto, player);
		
		return playerRepository.save(player);
		
	}
	

		public Player update(Long id, PlayerDTO dto) {
		
		Optional<Player> playerUpdate = playerRepository.findById(id);
		if (!playerUpdate.isPresent()) {
			throw new EntityNotFoundException();	
		}
		
		Player player = playerUpdate.get();
		BeanUtils.copyProperties(dto, player);
		
		
		return playerRepository.save(player);
		
	}
		
		public void cancella(Long id) {
			if (!playerRepository.existsById(id)) {
				throw new EntityNotFoundException("Player not found or already deleted");
			}
			
			playerRepository.findById(id).orElseThrow(() -> new RuntimeException("Player not found"));

	
			
			playerRepository.deleteById(id);
		}


}
