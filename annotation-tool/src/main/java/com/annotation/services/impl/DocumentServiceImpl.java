package com.annotation.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annotation.entities.Document;
import com.annotation.repositories.DocumentRepository;
import com.annotation.services.DocumentService;

@Service
public class DocumentServiceImpl implements DocumentService{

		@Autowired
		DocumentRepository documentRepo;
	
		public String readFileAsString(String uri) throws IOException {
			String data = "";
			data = new String(Files.readAllBytes(Paths.get(uri)));
			
			return data;
		}

		@Override
		public void deleteDocument(Long id) {
			documentRepo.deleteById(id);
			
		}

		@Override
		public void addDocument() {
			//TODO
			
		}

		@Override
		public List<Document> getDocumentsByCollection(Long collectionId) {
			return documentRepo.findByCollection(collectionId);
		}
}
