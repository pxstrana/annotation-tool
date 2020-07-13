package com.annotation.controllers;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.json.JSONParser;
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

import com.annotation.dto.TagGroupDTO;
import com.annotation.entities.Tag;
import com.annotation.entities.TagGroup;
import com.annotation.services.TagGroupService;
import com.annotation.services.TagService;


/**
 * Controller of the tag groups requests
 * 
 * @author Luis Pastrana García
 *
 */
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200", "http://localhost:8081" })
@RestController
@RequestMapping("/taggroup")
public class TagGroupController {
	
	
	@Autowired
	TagGroupService tagGroupService;
	
	@Autowired 
	TagService tagService;

	/**
	 * Returns all tag groups
	 * 
	 * @return tag groups and HttpStatus.OK if it is correct, HttpStatus.BAD_REQUEST if it is not
	 */
	@GetMapping("/all")
	public ResponseEntity<List<TagGroup>> getAllTagGroups(){
		
		try {
			List<TagGroup> lista = tagGroupService.getAll();
			return new ResponseEntity<List<TagGroup>>(lista,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<TagGroup>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	/**
	 * Adds new tag group 
	 * 
	 * @param dto the new tag group data
	 * @return HttpStatus.OK if it is correct, HttpStatus.BAD_REQUEST if it is not
	 */
	@PostMapping("/add")
	public ResponseEntity<Long> addTagGroup(@RequestBody TagGroupDTO dto){
			
		if( dto.getName()==null || dto.getName().length()== 0) return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		try {
			Long id= tagGroupService.add(new TagGroup(dto.getName(),dto.getDescription()));
			return new ResponseEntity<Long>(id,HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		}
		
	}
	/**
	 * Deletes a tag group by its id
	 * @param id the id of the tag group
	 * @return HttpStatus.OK if it is correct, HttpStatus.BAD_REQUEST if it is not
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteTagGroup(@PathVariable Long id){
		
		try {
			tagGroupService.delete(id);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>(HttpStatus.OK);
		
	}
	
	 
	
	/**
	 * Uploads a file with tags to create tag group
	 * 
	 * @param request the request with the data
	 * @param id the id of the tag group 
	 * @return HttpStatus.OK if it is correct, HttpStatus.BAD_REQUEST if it is not
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/upload/{id}")
	public ResponseEntity<String> uploadFile(HttpServletRequest request, @PathVariable Long id){
		
		ServletFileUpload upload = new ServletFileUpload();
		FileItemIterator iterStream;
		try {
			iterStream = upload.getItemIterator(request);
		

			while (iterStream.hasNext()) {
			     FileItemStream item = iterStream.next();
			     InputStream stream = item.openStream();
			     
			     if (!item.isFormField()) {
			    	 JSONParser jsonParser = new JSONParser(stream);
						
						
						Object jsonRequest = jsonParser.parse();
						if( !(jsonRequest instanceof LinkedHashMap<?, ?>)) throw new  FileUploadException();
						LinkedHashMap<String, Object> jsonList = (LinkedHashMap<String, Object>) jsonRequest ;
						
						ArrayList<LinkedHashMap<String, Object>> tags = (ArrayList<LinkedHashMap<String, Object>>) jsonList.get("tagGroup");
						for (LinkedHashMap<?, ?> object : tags) {
								String name = object.get("name").toString();
								String desc = object.get("description").toString();
								if(name == null) name="";
								if(desc == null) desc="";
								tagService.addTag(new Tag(name,desc), id);
						}
			    	 
			     } 
			 }
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
		
	}
		
		
}
