package com.annotation.services;

import java.util.List;
import java.util.NoSuchElementException;

import com.annotation.dto.CollectionDTO;
import com.annotation.entities.DocumentCollection;
import com.annotation.services.exceptions.AlreadyExistsException;
import com.annotation.services.exceptions.UserDoesNotExistException;

/**
 * CollectionService is an interface for all the operations related
 * with the DocumentColletions.
 * 
 * @author Luis
 *
 */
public interface CollectionService {

	/**
	 * List of all the collections in the database
	 * 
	 * @return List of all the collections
	 */
	List<DocumentCollection> getCollections();
	
	/**
	 * List of all the collections of a user
	 * 
	 * @param username to filter
	 * @return the list of collections
	 */
	List<DocumentCollection> getUserCollections(String username);

	
	/**
	 * Adds a collection to the database
	 * 
	 * @param documentCollection the document collection to be added
	 * @throws AlreadyExistsException the document already exists
	 */
	void addCollection(DocumentCollection documentCollection) throws AlreadyExistsException;
	
	
	/**
	 * Returns the collection with that name
	 * 
	 * @param name the name of the collection
	 * @return the collection 
	 */
	DocumentCollection getCollectionByName(String name);

	/**
	 * Returns the collection with the specified if
	 * 
	 * @param id the identifier of the collection
	 * @return the collection       
	 */
	DocumentCollection findCollection(Long id) throws NoSuchElementException;

	
	/**
	 * Deletes the collection with the id specified
	 * 
	 * @param id to be specified
	 * @return	true if it is deleted
	 * @throws NoSuchElementException Collection does not exist
	 */
	void deleteCollectionById(Long id);

	/**
	 * Update the collection with the new changes
	 *  
	 * @param collectionDTO the DTO with the information needed to make the changes
	 * @throws UserDoesNotExistException user does not exist
	 * @throws NoSuchElementException Collection does not exist
	 */
	void updateCollection(CollectionDTO collectionDTO) throws NoSuchElementException, UserDoesNotExistException;

	
}
