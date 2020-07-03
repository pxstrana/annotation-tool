package com.annotation.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.annotation.dto.CollectionDTO;
import com.annotation.entities.DocumentCollection;
import com.annotation.services.CollectionService;
import com.annotation.services.UsersService;
import com.annotation.services.exceptions.CollectionAlreadyExistsException;
import com.annotation.services.exceptions.UserDoesNotExistsException;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200", "http://localhost:8081" })
@RestController
@RequestMapping("/collection")
public class CollectionController {

	@Autowired
	CollectionService collectionService;

	@Autowired
	UsersService userService;

	@GetMapping("/all")
	public ResponseEntity<List<DocumentCollection>> getAllCollections() {

		return new ResponseEntity<List<DocumentCollection>>(collectionService.getCollections()
															,HttpStatus.OK);
	

	}

	@GetMapping("/user/{name}")
	public ResponseEntity<List<DocumentCollection>> getUserCollections(@PathVariable("name") String name) {
		return new ResponseEntity<List<DocumentCollection>>(collectionService.getUserCollections(name)
					,HttpStatus.OK);

	}

	@GetMapping("me")
	public ResponseEntity<List<DocumentCollection>> getMyCollections() {
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		return user == null ? 
				new ResponseEntity<List<DocumentCollection>>(HttpStatus.BAD_REQUEST) 
			  : new ResponseEntity<List<DocumentCollection>>(collectionService.getUserCollections(user)
						,HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DocumentCollection> getCollection(@PathVariable(name = "id") Long id) {
		try {
			DocumentCollection collection = collectionService.findCollection(id);
			return new ResponseEntity<DocumentCollection>(collection,HttpStatus.OK);
		}catch (NoSuchElementException e) {
			return new ResponseEntity<DocumentCollection>(HttpStatus.BAD_REQUEST);
		}
		
	}

	@PostMapping("/add")
	public ResponseEntity<String> addCollection(@RequestBody CollectionDTO dto) {

		try {
			DocumentCollection collection = new DocumentCollection(dto.getName(), 
																(String) dto.getDescription());
			collectionService.addCollection(collection);
			
			userService.addUsersToCollection(collection,dto.getUsersIds());
			
			
		} catch (UserDoesNotExistsException e) {
			return new ResponseEntity<String>("Users do not exist",HttpStatus.BAD_REQUEST);
		}catch(CollectionAlreadyExistsException e2) {
			return new ResponseEntity<String>("Collection already exists",HttpStatus.BAD_REQUEST);
		}
 
		return new ResponseEntity<String>(HttpStatus.OK);

	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Long> deleteCollection(@PathVariable Long id){
		 boolean isRemoved = collectionService.deleteCollectionById(id);
		 
		 if(!isRemoved) {
			 return new ResponseEntity<Long>(HttpStatus.NOT_FOUND);
		 }
		return new ResponseEntity<Long>	(id, HttpStatus.OK);
	}
	
	
	@PostMapping("/update")
	public ResponseEntity<String> updateCollection(@RequestBody CollectionDTO collectionDTO){
		
		try {
		collectionService.updateCollection(collectionDTO);
		}catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>(HttpStatus.OK);
		
		
		
		
	}

}
