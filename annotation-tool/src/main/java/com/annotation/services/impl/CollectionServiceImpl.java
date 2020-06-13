package com.annotation.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annotation.entities.DocumentCollection;
import com.annotation.repositories.CollectionRepository;
import com.annotation.services.CollectionService;

@Service
public class CollectionServiceImpl implements CollectionService{

	@Autowired
	CollectionRepository collectionRepo;
	
	@Override
	public List<DocumentCollection> getCollections() {
		List<DocumentCollection> collections = new ArrayList<>();
		collectionRepo.findAll().forEach(collections::add);
		return collections;
	}

	@Override
	public List<DocumentCollection> getUserCollections(String username) {
		return collectionRepo.findByUsername(username);
	}

	@Override
	public void addCollection(DocumentCollection collection) {
		if( collection != null && collectionRepo.findByName(collection.getName()) == null  ) {
			collectionRepo.save(collection);
		}else {
			throw new RuntimeException("Repeated collection");
		}
		
	}

	@Override
	public void deleteCollectionByName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DocumentCollection getCollectionByName(String name) {
		return collectionRepo.findByName(name);
		
	}

	@Override
	public DocumentCollection findCollection(Long id) {
		
		return collectionRepo.findById(id).get();
	}

}
