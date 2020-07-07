package com.annotation.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annotation.dto.CollectionDTO;
import com.annotation.entities.DocumentCollection;
import com.annotation.repositories.CollectionRepository;
import com.annotation.services.CollectionService;
import com.annotation.services.DocumentService;
import com.annotation.services.UsersService;
import com.annotation.services.exceptions.CollectionAlreadyExistsException;
import com.annotation.services.exceptions.UserDoesNotExistsException;

@Service
public class CollectionServiceImpl implements CollectionService{

	@Autowired
	CollectionRepository collectionRepo;
	
	@Autowired
	UsersService userService;
	
	@Autowired
	DocumentService documentService;

	
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
	public void addCollection(DocumentCollection collection) throws CollectionAlreadyExistsException {
		if( collection != null && collectionRepo.findByName(collection.getName()) == null  ) {
			collectionRepo.save(collection);
		}else {
			throw new CollectionAlreadyExistsException("Repeated collection");
		}
		
	}


	@Override
	public DocumentCollection getCollectionByName(String name) {
		return collectionRepo.findByName(name);
		
	}

	@Override
	public DocumentCollection findCollection(Long id) throws NoSuchElementException{
		
		return collectionRepo.findById(id).get();
	}

	@Override
	public boolean deleteCollectionById(Long id) {
		
		DocumentCollection collection = collectionRepo.findById(id).orElse(null);
		if(collection!=null) {
			
			collection.getDocuments().forEach(doc->{
				documentService.deleteDocument(doc.getId());
				doc.setCollection(null);
			});
			collection.getUsersAllowed().forEach(user->user.getCollections().remove(collection));
			
			
			collectionRepo.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public void updateCollection(CollectionDTO collectionDTO) throws NoSuchElementException, UserDoesNotExistsException {

		DocumentCollection collection = collectionRepo.findById(collectionDTO.getId()).get();
		
		collection.getUsersAllowed().forEach(user->user.getCollections().remove(collection));	
		userService.addUsersToCollection(collection, collectionDTO.getUsersIds());
		collection.setDescription(collectionDTO.getDescription());
		collectionRepo.save(collection);
		
		
		
	}

	



}
