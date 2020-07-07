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

import com.annotation.dto.LayerDTO;
import com.annotation.entities.Layer;
import com.annotation.services.LayerService;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200", "http://localhost:8081" })
@RestController
@RequestMapping("/layer")
public class LayerController {
	
	@Autowired
	LayerService layerService;
	
	@GetMapping("/{docId}")
	public ResponseEntity<List<Layer>> getLayers(@PathVariable Long docId){
		
		List<Layer> layers = layerService.getLayers(docId);
		return new ResponseEntity<List<Layer>>(layers,HttpStatus.OK);
		
	}
	
	@PostMapping("/add")
	public ResponseEntity<String> addLayer(@RequestBody LayerDTO layerDTO){
		
		
		try {
			layerService.addLayer(layerDTO);	
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
		
	}
	
	@DeleteMapping("/delete/{id}")	
	public ResponseEntity<String> deleteLayer(@PathVariable Long id ){
		try {
			layerService.deleteLayer(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	

}
