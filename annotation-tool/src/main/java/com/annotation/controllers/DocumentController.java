package com.annotation.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.annotation.services.DocumentService;

@Controller
public class DocumentController {

	public static final String ERROR="error";

	@Autowired
	DocumentService documentService;
	
	@GetMapping(value = "/document/show")
	public String document(Model model, Principal principal) {

		
		return "document/showDoc";
	}
	
	@GetMapping(value = "/document/show/{id}")
	public String documentDetails(Model model, Principal principal, @PathVariable Long id) {
		
		try {
			String texto = documentService.readFileAsString("C:/Users/Luis/git/annotation-tool/textoGrande.txt");
			model.addAttribute("text",texto);
		} catch (IOException e) {
			model.addAttribute(ERROR,true);
		}
		
		return "document/showDoc";
	}
	
	@PostMapping(value="/document/load")
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
