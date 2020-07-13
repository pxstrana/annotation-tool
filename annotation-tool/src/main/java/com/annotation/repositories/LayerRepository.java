package com.annotation.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.annotation.entities.Layer;

public interface LayerRepository extends CrudRepository<Layer, Long>{

	/**
	 * Returns the layers of a document
	 *  
	 * @param docId the id of the document
	 * @return List of layers
	 */
	@Query("SELECT l FROM Layer l WHERE l.document.id = (?1)")
	List<Layer> getLayerByDocumentId(Long docId);

}
