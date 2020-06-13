package com.annotation.services;

import java.util.List;

import com.annotation.entities.DocumentCollection;

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
	 */
	void addCollection(DocumentCollection documentCollection);
	
	/**
	 * Deletes a collection by its name
	 * 
	 * @param name to be searched
	 */
	void deleteCollectionByName(String name);
	
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
	DocumentCollection findCollection(Long id);
	
}
