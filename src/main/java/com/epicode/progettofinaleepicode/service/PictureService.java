package com.epicode.progettofinaleepicode.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import com.epicode.progettofinaleepicode.entity.Jersey;
import com.epicode.progettofinaleepicode.entity.JerseyDto;
import com.epicode.progettofinaleepicode.entity.News;
import com.epicode.progettofinaleepicode.entity.Picture;
import com.epicode.progettofinaleepicode.entity.PictureDTO;
import com.epicode.progettofinaleepicode.entity.Player;
import com.epicode.progettofinaleepicode.entity.Squadre;
import com.epicode.progettofinaleepicode.entity.Stadium;
import com.epicode.progettofinaleepicode.repository.JerseyRepository;
import com.epicode.progettofinaleepicode.repository.PictureRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PictureService {

	private PictureRepository pictureRepository;
	
	private ObjectProvider<Picture> pictureProvider;
	
	private ObjectProvider<PictureDTO> pictureDtoProvider;

	public List<Picture> getAll() {
		return pictureRepository.findAll();
	}
	
	public Optional<Picture> getById(Long id) {
		Optional<Picture>  jersey = pictureRepository.findById(id);
		if (jersey.isPresent()) return jersey;
		
		throw new EntityNotFoundException("Picture non trovato");
				
	}
	
	public Picture getById2(Long id) {
		if(pictureRepository.existsById(id)) {
			return pictureRepository.findById(id).get();
		}
		throw new EntityNotFoundException("Picture non trovato");
	}
	

	public Picture insert(PictureDTO dto) {
//		if(jerseyRepository.existsByColor(dto.getColor())) {
//			throw new EntityExistsException("Jersey gia inserito");
//		}
		
		Picture picture = pictureProvider.getObject();
		BeanUtils.copyProperties(dto, picture);
		
		return pictureRepository.save(picture);
		
	}
	

		public Picture update(Long id, PictureDTO dto) {
		Optional<Picture> pictureUpdate = pictureRepository.findById(id);
		if (!pictureUpdate.isPresent()) {
			throw new EntityNotFoundException();	
		}
		
		Picture picture = pictureUpdate.get();
		BeanUtils.copyProperties(dto, picture);
		
		return pictureRepository.save(picture);
		
	}
		
		public void cancella(Long id) {
			
			Picture picture = pictureRepository.findById(id).orElseThrow(() -> new RuntimeException("Picture not found"));
			
			
//			List <Stadium> stadium = picture.getStadium();		
//			if (stadium != null && !stadium.isEmpty()){
//
//		        throw new IllegalStateException("Cannot delete the Picture as it is already linked to a stadium.");
//
//			}
//			
//			List <Player> player = picture.getPlayer();		
//			if (player != null && !player.isEmpty()){
//
//		        throw new IllegalStateException("Cannot delete the Picture as it is already linked to a player.");
//
//			}
//			
//			List <News> news = picture.getNews();		
//			if (news != null && !news.isEmpty()){
//
//		        throw new IllegalStateException("Cannot delete the Picture as it is already linked to a news.");
//
//			}
			
			pictureRepository.deleteById(id);
		}
		
		
		

}

