package com.annotation.services.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annotation.dto.AnnotationDTO;
import com.annotation.entities.Annotation;
import com.annotation.entities.Layer;
import com.annotation.repositories.AnnotationRepository;
import com.annotation.repositories.LayerRepository;
import com.annotation.services.AnnotationService;

@Service
public class AnnotationServiceImpl implements AnnotationService {
	
	
	@Autowired
	AnnotationRepository annotationRepo;
	
	@Autowired
	LayerRepository layerRepo;
	
	@Override
	public List<Annotation> getAnnotationsOfLayer(Long layer) {
		return annotationRepo.getAnnotationsOfLayer(layer);
		
	}


	@Override
	public void deleteAnnotation(Long id) throws IllegalArgumentException, NoSuchElementException{
	
		annotationRepo.findById(id).get();
		annotationRepo.deleteById(id);
		
	}


	@Override
	public void addAnnotation(AnnotationDTO dto) throws NoSuchElementException{
		
		Layer layer = layerRepo.findById(dto.getLayerId()).get();		
		Annotation annotation = new Annotation(dto.getTag(),dto.getStartOffset(),dto.getEndOffset());
		layer.addAnnotation(annotation);
		annotationRepo.save(annotation);
		
	}
}
