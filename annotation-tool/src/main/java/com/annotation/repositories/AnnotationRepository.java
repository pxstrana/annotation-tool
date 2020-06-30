package com.annotation.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.annotation.entities.Annotation;

public interface AnnotationRepository extends CrudRepository<Annotation, Long>{

	@Query("SELECT a FROM Annotation a WHERE a.layer.id = (?1)")
	List<Annotation> getAnnotationsOfLayer(Long layer);
}
