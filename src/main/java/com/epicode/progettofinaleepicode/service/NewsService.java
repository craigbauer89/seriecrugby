package com.epicode.progettofinaleepicode.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import com.epicode.progettofinaleepicode.entity.News;
import com.epicode.progettofinaleepicode.entity.NewsDto;
import com.epicode.progettofinaleepicode.entity.Classifica;
import com.epicode.progettofinaleepicode.entity.Season;
import com.epicode.progettofinaleepicode.repository.NewsRepository;
import com.epicode.progettofinaleepicode.repository.SeasonRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NewsService {
	
	private NewsRepository  newsRepository;
	
	private ObjectProvider<News> newsProvider;
	

	public List<News> getAll() {
		return newsRepository.findAll();
	}
	

	public Optional<News> getById(Long id) {
		Optional<News>  cliente = newsRepository.findById(id);
		if (cliente.isPresent()) return cliente;
		
		throw new EntityNotFoundException("News non trovato");
				
	}
	

	public News insert(NewsDto dto) {
		if(newsRepository.existsByTitle(dto.getTitle())) {
			throw new EntityExistsException("News gia inserito");
		}

		News news = newsProvider.getObject();
		BeanUtils.copyProperties(dto, news);
		
		return newsRepository.save(news);
		
	}
	

		public News update(Long id, NewsDto dto) {
		
		Optional<News> newsUpdate = newsRepository.findById(id);
		if (!newsUpdate.isPresent()) {
			throw new EntityNotFoundException();	
		}
		
		News news = newsUpdate.get();
		BeanUtils.copyProperties(dto, news);
		
		
		return newsRepository.save(news);
		
	}
		
		public void cancella(Long id) {
			if (!newsRepository.existsById(id)) {
				throw new EntityNotFoundException("News not found or already deleted");
			}
			
			
			newsRepository.deleteById(id);
		}

}
