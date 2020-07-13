package com.annotation.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.annotation.entities.DocumentCollection;



public interface CollectionRepository extends CrudRepository<DocumentCollection, Long>{

	
	/**
	 * Returns the DocumentCollection with that name
	 * 
	 * @param name the name of the collection
	 * @return the DocumentCollection with that name
	 */
	DocumentCollection findByName(String name);
	
	/**
	 * Returns the list of Document Collections allowed to the specified user
	 * 
	 * @param username the username of the user
	 * @return List of Document Collections
	 */
	@Query("SELECT u.collections FROM appUser u WHERE u.username = (?1)")
    List<DocumentCollection> findByUsername( String username);
}
