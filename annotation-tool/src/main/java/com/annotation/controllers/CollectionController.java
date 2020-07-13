package com.annotation.controllers;

import java.util.List;
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
import com.annotation.services.exceptions.AlreadyExistsException;
import com.annotation.services.exceptions.UserDoesNotExistException;


/**
 * Controller of the Collection requests
 * 
 * @author Luis Pastrana García
 *
 */
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200", "http://localhost:8081" })
@RestController
@RequestMapping("/collection")
public class CollectionController {

	@Autowired
	CollectionService collectionService;

	@Autowired
	UsersService userService;

	/**
	 * Returns all the DocumentCollections
	 * 
	 * @return Response 200 http status 
	 */
	@GetMapping("/all")
	public ResponseEntity<List<DocumentCollection>> getAllCollections() {

		return new ResponseEntity<List<DocumentCollection>>(collectionService.getCollections()
															,HttpStatus.OK);
	

	}

	/**
	 * Returns users collections 
	 * @deprecated
	 * @param name the name of the user
	 * @return the Collections of the user with Response 200
	 */
	@GetMapping("/user/{name}")
	public ResponseEntity<List<DocumentCollection>> getUserCollections(@PathVariable("name") String name) {
		return new ResponseEntity<List<DocumentCollection>>(collectionService.getUserCollections(name)
					,HttpStatus.OK);

	}

	/**
	 * Returns user collections
	 * @return Response 200 with the DocumentCollection list if it is correct, 400 if is not.
	 */
	@GetMapping("me")
	public ResponseEntity<List<DocumentCollection>> getMyCollections() {
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		return user == null ? 
				new ResponseEntity<List<DocumentCollection>>(HttpStatus.BAD_REQUEST) 
			  : new ResponseEntity<List<DocumentCollection>>(collectionService.getUserCollections(user)
						,HttpStatus.OK);
	}
	/**
	 * Returns the DocumentCollection by the id
	 * @param id the identifier of the DocumentCollection
	 * @return Response 200 with the DocumentCollection if is correct
	 * 			400 if is not
	 */
	@GetMapping("/{id}")
	public ResponseEntity<DocumentCollection> getCollection(@PathVariable(name = "id") Long id) {
		try {
			DocumentCollection collection = collectionService.findCollection(id);
			return new ResponseEntity<DocumentCollection>(collection,HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<DocumentCollection>(HttpStatus.BAD_REQUEST);
		}
		
	}

	/**
	 * Adds a collection
	 * @param dto the CollectionDTO with the data
	 * @return Response 200 if it is correct, 400 if is not
	 */
	@PostMapping("/add")
	public ResponseEntity<String> addCollection(@RequestBody CollectionDTO dto) {

		try {
			DocumentCollection collection = new DocumentCollection(dto.getName(), 
																(String) dto.getDescription());
			collectionService.addCollection(collection);
			
			userService.addUsersToCollection(collection,dto.getUsersIds());
			
			
		} catch (UserDoesNotExistException e) {
			return new ResponseEntity<String>("Users do not exist",HttpStatus.BAD_REQUEST);
		}catch(AlreadyExistsException e2) {
			return new ResponseEntity<String>("Collection already exists",HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
 
		return new ResponseEntity<String>(HttpStatus.OK);

	}
	
	/**
	 * Delete collection by id
	 * @param id the identifier of the collection
	 * @return Response 200 with the id if it is correct, 400 if is not
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Long> deleteCollection(@PathVariable Long id){
		
		try {
		  collectionService.deleteCollectionById(id);

		}catch(Exception e) {
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Long>	(id, HttpStatus.OK);
	}
	
	/**
	 * Updates the collection by the new data sent in the DTO
	 * 
	 * @param collectionDTO the dto with the new data
	 * @return Response 200 if it is correct, 400 if it is not
	 */
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
