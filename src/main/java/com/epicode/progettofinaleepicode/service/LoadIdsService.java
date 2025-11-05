package com.epicode.progettofinaleepicode.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import com.epicode.progettofinaleepicode.entity.Channel;
import com.epicode.progettofinaleepicode.entity.ChannelDto;
import com.epicode.progettofinaleepicode.entity.LoadIds;
import com.epicode.progettofinaleepicode.entity.LoadIdsDto;
import com.epicode.progettofinaleepicode.repository.ChannelRepository;
import com.epicode.progettofinaleepicode.repository.LoadIdsRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoadIdsService {
	
private LoadIdsRepository  loadIdsRepository;
	
	private ObjectProvider<LoadIds> loadIdsProvider;
	

	public List<LoadIds> getAll() {
		return loadIdsRepository.findAll();
	}
	

	public Optional<LoadIds> getById(Long id) {
		Optional<LoadIds>  cliente = loadIdsRepository.findById(id);
		if (cliente.isPresent()) return cliente;
		
		throw new EntityNotFoundException("LoadIds non trovato");
				
	}
	

	public LoadIds insert(LoadIdsDto dto) {


		LoadIds loadIds = new LoadIds();
		BeanUtils.copyProperties(dto, loadIds);
		
		return loadIdsRepository.save(loadIds);
		
	}
	

		public LoadIds update(Long id, LoadIdsDto dto) {
		
		Optional<LoadIds> loadIdsUpdate = loadIdsRepository.findById(id);
		if (!loadIdsUpdate.isPresent()) {
			throw new EntityNotFoundException();	
		}
		
		LoadIds loadIds = loadIdsUpdate.get();
		BeanUtils.copyProperties(dto, loadIds);
		
		
		return loadIdsRepository.save(loadIds);
		
	}
		
		public void cancella(Long id) {
			if (!loadIdsRepository.existsById(id)) {
				throw new EntityNotFoundException("LoadIds not found or already deleted");
			}
			
			loadIdsRepository.findById(id).orElseThrow(() -> new RuntimeException("LoadIds not found"));


			loadIdsRepository.deleteById(id);
		}



}
