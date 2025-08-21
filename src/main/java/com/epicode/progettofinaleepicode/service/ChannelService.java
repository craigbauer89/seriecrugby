package com.epicode.progettofinaleepicode.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import com.epicode.progettofinaleepicode.entity.Championship;
import com.epicode.progettofinaleepicode.entity.Channel;
import com.epicode.progettofinaleepicode.entity.ChannelDto;
import com.epicode.progettofinaleepicode.entity.Classifica;
import com.epicode.progettofinaleepicode.entity.News;
import com.epicode.progettofinaleepicode.entity.NewsDto;
import com.epicode.progettofinaleepicode.repository.ChampionshipRepository;
import com.epicode.progettofinaleepicode.repository.ChannelRepository;
import com.epicode.progettofinaleepicode.repository.NewsRepository;
import com.epicode.progettofinaleepicode.repository.SeasonRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChannelService {
	
	private ChannelRepository  channelRepository;
	
	private ObjectProvider<Channel> channelProvider;
	

	public List<Channel> getAll() {
		return channelRepository.findAll();
	}
	

	public Optional<Channel> getById(Long id) {
		Optional<Channel>  cliente = channelRepository.findById(id);
		if (cliente.isPresent()) return cliente;
		
		throw new EntityNotFoundException("Channel non trovato");
				
	}
	

	public Channel insert(ChannelDto dto) {
		if(channelRepository.existsByName(dto.getName())) {
			throw new EntityExistsException("Channel gia inserito");
		}

		Channel channel = channelProvider.getObject();
		BeanUtils.copyProperties(dto, channel);
		
		return channelRepository.save(channel);
		
	}
	

		public Channel update(Long id, ChannelDto dto) {
		
		Optional<Channel> channelUpdate = channelRepository.findById(id);
		if (!channelUpdate.isPresent()) {
			throw new EntityNotFoundException();	
		}
		
		Channel channel = channelUpdate.get();
		BeanUtils.copyProperties(dto, channel);
		
		
		return channelRepository.save(channel);
		
	}
		
		public void cancella(Long id) {
			if (!channelRepository.existsById(id)) {
				throw new EntityNotFoundException("Channel not found or already deleted");
			}
			
			channelRepository.findById(id).orElseThrow(() -> new RuntimeException("Channel not found"));


			channelRepository.deleteById(id);
		}


}
