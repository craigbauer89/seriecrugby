package com.epicode.progettofinaleepicode.api;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apiCompetitions")
@CrossOrigin
public class RugbyApiController {
	
	private final RugbyApiService rugbyApiService;
	
	
    public RugbyApiController(RugbyApiService rugbyApiService) {
        this.rugbyApiService = rugbyApiService;
    }
	
	 @GetMapping
	    public CompletableFuture<String> getFixtures() {
	        return rugbyApiService.getCompetitions();
	    }
	

}
