package com.annotation.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.annotation.dto.AnnotationDTO;
import com.annotation.entities.Annotation;
import com.annotation.services.AnnotationService;


@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200", "http://localhost:8081" })
@RestController
@RequestMapping("/annotation")
public class AnnotationController {

	@Autowired
	AnnotationService annotationService;
	
	@GetMapping("/list/{layer}")
	public ResponseEntity<List<Annotation>> getAnnotationsOfLayer(@PathVariable Long layer){
		
		return new ResponseEntity<List<Annotation>>( annotationService.getAnnotationsOfLayer(layer), HttpStatus.OK);
		
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteAnnotation (@PathVariable Long id){
		
		try {
		annotationService.deleteAnnotation(id);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<String> addAnnotation(@RequestBody AnnotationDTO annotationDTO){
		
		try {
			annotationService.addAnnotation(annotationDTO);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>(HttpStatus.OK);
		
	}
	
	
	
	
	
	
}
