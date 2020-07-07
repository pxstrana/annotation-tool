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

import com.annotation.entities.Tag;
import com.annotation.services.TagService;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200", "http://localhost:8081" })
@RestController
@RequestMapping("/tag")
public class TagController {
	
	@Autowired
	TagService tagService;
	
	@PostMapping("/add/{id}")
	public ResponseEntity<String> addTag(@RequestBody Tag tag,@PathVariable Long id){
		
		try {
			tagService.addTag(tag, id);
			return new ResponseEntity<String>(HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
		
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteTag(@PathVariable Long id){
		
		try {
			tagService.deleteTag(id);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	 @GetMapping("/tags/{id}")
	 public ResponseEntity<List<Tag>> getTagsOfGroup(@PathVariable Long id){
		 try {
			 List<Tag> lista= tagService.getTagsOfGroup(id);
			 return new ResponseEntity<List<Tag>>(lista,HttpStatus.OK);
		 }catch (Exception e) {
			 return new ResponseEntity<List<Tag>>(HttpStatus.BAD_REQUEST);
		}
		 
	 }
	
	
	
	

}
