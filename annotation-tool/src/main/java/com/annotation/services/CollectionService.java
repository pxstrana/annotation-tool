package com.annotation.services;

import java.util.List;
import java.util.NoSuchElementException;

import com.annotation.dto.CollectionDTO;
import com.annotation.entities.DocumentCollection;
import com.annotation.services.exceptions.CollectionAlreadyExistsException;
import com.annotation.services.exceptions.UserDoesNotExistsException;

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
	 * @throws CollectionAlreadyExistsException 
	 */
	void addCollection(DocumentCollection documentCollection) throws CollectionAlreadyExistsException;
	
	
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
	 */
	boolean deleteCollectionById(Long id);

	/**
	 * Update the collection with the new changes
	 *  
	 * @param collectionDTO the DTO with the information needed to make the changes
	 * @throws UserDoesNotExistsException 
	 * @throws NoSuchElementException 
	 */
	void updateCollection(CollectionDTO collectionDTO) throws NoSuchElementException, UserDoesNotExistsException;

	
}
