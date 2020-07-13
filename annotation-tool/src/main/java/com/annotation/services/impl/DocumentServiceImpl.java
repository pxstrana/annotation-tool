package com.annotation.services.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annotation.dto.DocumentDTO;
import com.annotation.entities.Document;
import com.annotation.entities.Layer;
import com.annotation.repositories.DocumentRepository;
import com.annotation.repositories.LayerRepository;
import com.annotation.services.DocumentService;

@Service
public class DocumentServiceImpl implements DocumentService{

		@Autowired
		DocumentRepository documentRepo;
		
		@Autowired
		LayerRepository layerRepo;
		
		
		@Autowired
		CollectionServiceImpl collectionRepo;
		
	
		public String readFileAsString(String uri) throws IOException {
			String data = "";
			data = new String(Files.readAllBytes(Paths.get(uri)));
			
			return data;
		}

		@Override
		public void deleteDocument(Long id) throws IllegalArgumentException, NoSuchElementException, IOException{
			
			Document doc = documentRepo.findById(id).get();
			System.out.println(doc.toString());
	
			FileUtils.forceDelete(new File(doc.getUri()));
			documentRepo.deleteById(id);
			
			
		}

	

		@Override
		public List<Document> getDocumentsByCollection(Long collectionId) {
			return documentRepo.findByCollectionId(collectionId);
		}

		@Override
		public void addDocument(Document document, Long idCollection) {
			Layer layer = new Layer("Main");
			document.addLayer(layer);
			collectionRepo.findCollection(idCollection).addDocument(document);
			documentRepo.save(document);
			layerRepo.save(layer);

			
		}

		@Override
		public Document getDocumentById(Long documentId) {
			return documentRepo.findById(documentId).get();
			
		}

		@Override
		public void modifyDocument(DocumentDTO document) throws NoSuchElementException {
			Long id=document.getId();
			if( id == null) {throw new NoSuchElementException("The id was null");}
			Document doc = documentRepo.findById(id).get();
			doc.setName(document.getName());
			doc.setDescription(document.getDescription());
			documentRepo.save(doc);
		
			
			
		}

	
}
