package com.annotation.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annotation.dto.DocumentDTO;
import com.annotation.entities.Document;
import com.annotation.entities.Layer;
import com.annotation.repositories.DocumentRepository;
import com.annotation.repositories.LayerRepository;
import com.annotation.services.DocumentService;
import com.annotation.services.exceptions.DocumentDoesNotExistException;

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
		public void deleteDocument(Long id) throws IllegalArgumentException{
			documentRepo.deleteById(id);
			//TODO: delete from system files.
			// get doc, save url delet:  new File(url).delete() excepcion si no se pudo borrar
		}

	

		@Override
		public List<Document> getDocumentsByCollection(Long collectionId) {
			return documentRepo.findByCollection(collectionId);
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
		public void modifyDocument(DocumentDTO document) throws DocumentDoesNotExistException {
			Long id=document.getId();
			if( id == null) {throw new DocumentDoesNotExistException("The id was null");}
			Document doc = documentRepo.findById(id).get();
			doc.setNombre(document.getName());
			doc.setDescription(document.getDescription());
			documentRepo.save(doc);
		
			
			
		}

	
}
