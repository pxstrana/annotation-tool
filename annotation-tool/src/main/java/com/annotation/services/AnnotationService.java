package com.annotation.services;

import java.util.List;
import java.util.NoSuchElementException;

import com.annotation.dto.AnnotationDTO;
import com.annotation.entities.Annotation;

public interface AnnotationService {

	
	/**
	 * Returns the annotation list of a layer
	 * 
	 * @param layer the layer of the annotations
	 * @return List of Annotations
	 */
	List<Annotation> getAnnotationsOfLayer( Long layer);

	/**
	 * Deletes the annotation  
	 * 
	 * @param id the id of the annotation
	 */
	void deleteAnnotation(Long id) throws IllegalArgumentException , NoSuchElementException;

	
	/**
	 * Adds the annotation
	 * 
	 * @param annotationDTO the annotation to add
	 */
	void addAnnotation(AnnotationDTO annotationDTO);
}
