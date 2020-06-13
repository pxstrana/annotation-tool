package com.annotation.controllers;

import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.annotation.entities.Document;
import com.annotation.services.DocumentService;
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200", "http://localhost:8081" })
@RestController
@RequestMapping("/document")
public class DocumentController {

	public static final String ERROR="error";

	@Autowired
	DocumentService documentService;
	
	/**
	 * Retrieve all the documents of one collection
	 * 
	 * @param id the id of the collection
	 * @return 
	 */
	@GetMapping("/collection/{id}")
	public List<Document> getDocuments(@PathVariable(name="id") Long collectionId) {
		return documentService.getDocumentsByCollection(collectionId);
	}
	
//	@GetMapping(value = "/document/show/{id}")
//	public String documentDetails(Model model, Principal principal, @PathVariable Long id) {
//		
//		try {
//			String texto = documentService.readFileAsString("C:/Users/Luis/git/annotation-tool/textoGrande.txt");
//			model.addAttribute("text",texto);
//		} catch (IOException e) {
//			model.addAttribute(ERROR,true);
//		}
//		
//		return "document/showDoc";
//	}
//	
	@PostMapping(value="/load")
	public String loadDocument(Model model,@RequestParam("uri") String payload) {
		System.out.println(uriDecoder(payload));
		try {
			String texto = documentService.readFileAsString(uriDecoder(payload));
			model.addAttribute("text",texto);
		} catch (IOException e) {
			model.addAttribute(ERROR,true);
		}
		
		return "document/showDoc";
	}
	
	
	
	//TODO: move to other class
	private String uriDecoder(String uri) {
		String result = null;
		try {
		     result = java.net.URLDecoder.decode(uri, StandardCharsets.UTF_8.name());
		     result=Paths.get(result).toString();
		} catch (UnsupportedEncodingException e) {
			
		}
		return result;
	}
}
