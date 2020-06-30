package com.annotation.services;

import java.util.List;

import com.annotation.dto.LayerDTO;
import com.annotation.entities.Layer;


public interface LayerService {

	/**
	 * Adds a layer to an specified document
	 * 
	 * @param layerDTO layer information
	 */
	void addLayer(LayerDTO layerDTO);

	
	/**
	 * Return the layers of a document
	 * 
	 * @param docId the id of the document
	 * @return all the layers of the document
	 */
	List<Layer> getLayers(Long docId);


	/**
	 * Deletes the layer with the id given
	 * 
	 * @param id the id of the layer 
	 */
	void deleteLayer(Long id);

}
