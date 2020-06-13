package com.annotation.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.annotation.entities.DocumentCollection;
import com.annotation.services.CollectionService;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200", "http://localhost:8081" })
@RestController
@RequestMapping("/collection")
public class CollectionController {

	@Autowired
	CollectionService collectionService;
	
	
	@GetMapping("/all")
	public List<DocumentCollection> getAllCollections(){
		
		return collectionService.getCollections();
	
	}
	
	@GetMapping("/user/{name}")
	public List<DocumentCollection> getUserCollections(@PathVariable("name") String name){
		return collectionService.getUserCollections(name);
		
	}
	
	@GetMapping("me")
	public List<DocumentCollection> getMyCollections(){
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		return user == null ? null : collectionService.getUserCollections(user);
	}
	
	
	@GetMapping("/{id}")
	public DocumentCollection getCollection(@PathVariable(name = "id") Long id) {
		return collectionService.findCollection(id);
	}
	
}
