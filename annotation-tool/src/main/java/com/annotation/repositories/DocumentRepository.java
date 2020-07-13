package com.annotation.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.annotation.entities.Document;


public interface DocumentRepository extends CrudRepository<Document, Long>{

	/**
	 * Returns the list of documents of a collection
	 * 
	 * @param collectionId the id of the collection
	 * @return List of documents
	 */
	@Query("SELECT d FROM Document d WHERE d.collection.id  = (?1)")
	List<Document> findByCollectionId(Long collectionId);

	

}
