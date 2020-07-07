package com.annotation.services.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annotation.dto.LayerDTO;
import com.annotation.entities.Document;
import com.annotation.entities.Layer;
import com.annotation.repositories.DocumentRepository;
import com.annotation.repositories.LayerRepository;
import com.annotation.services.LayerService;

@Service
public class LayerServiceImpl implements LayerService {

	
	@Autowired
	LayerRepository layerRepo;
	
	@Autowired
	DocumentRepository documentRepo;
	
	@Override
	public void addLayer(LayerDTO layerDTO) {
		Layer layer = new Layer(layerDTO.getName());
		Document doc = documentRepo.findById(layerDTO.getDocumentId()).get();
		doc.addLayer(layer);
		
		layerRepo.save(layer);
		
	}

	@Override
	public List<Layer> getLayers(Long docId) {
		
		return layerRepo.getLayerByDocumentId(docId);
		
	}

	@Override
	public void deleteLayer(Long id) throws NoSuchElementException{
		
		
		layerRepo.deleteById(id);
		
	}

}
