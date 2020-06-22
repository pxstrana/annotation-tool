package com.annotation.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.annotation.entities.DocumentCollection;



public interface CollectionRepository extends CrudRepository<DocumentCollection, Long>{

	
	
	DocumentCollection findByName(String name);
	
	@Query("SELECT u.collections FROM appUser u WHERE u.username = (?1)")
    List<DocumentCollection> findByUsername( String username);
}
