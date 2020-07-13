package com.annotation.services.impl;

import java.io.IOException;
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
import com.annotation.services.exceptions.AlreadyExistsException;
import com.annotation.services.exceptions.UserDoesNotExistException;

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
	public void addCollection(DocumentCollection collection) throws AlreadyExistsException {
		if( collection != null && collectionRepo.findByName(collection.getName()) == null  ) {
			collectionRepo.save(collection);
		}else {
			throw new AlreadyExistsException("Repeated collection");
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
	public void deleteCollectionById(Long id) throws NoSuchElementException {
		
			DocumentCollection collection = collectionRepo.findById(id).get();

			collection.getDocuments().forEach(doc->{
				try {
					documentService.deleteDocument(doc.getId());
				} catch (IllegalArgumentException | NoSuchElementException | IOException e) {
					throw new RuntimeException("Error while deleting document");
				}
				doc.setCollection(null);
			});
			collection.getUsersAllowed().forEach(user->user.getCollections().remove(collection));
			
			
			collectionRepo.deleteById(id);

	}

	@Override
	public void updateCollection(CollectionDTO collectionDTO) throws NoSuchElementException, UserDoesNotExistException {

		DocumentCollection collection = collectionRepo.findById(collectionDTO.getId()).get();
		
		collection.getUsersAllowed().forEach(user->user.getCollections().remove(collection));	
		userService.addUsersToCollection(collection, collectionDTO.getUsersIds());
		collection.setDescription(collectionDTO.getDescription());
		collectionRepo.save(collection);
		
		
		
	}

	



}
