package com.annotation.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.annotation.entities.Document;


public interface DocumentRepository extends CrudRepository<Document, Long>{

	@Query("SELECT d FROM Document d WHERE d.collection.id = (?1)")
	List<Document> findByCollection(Long collectionId);

}
