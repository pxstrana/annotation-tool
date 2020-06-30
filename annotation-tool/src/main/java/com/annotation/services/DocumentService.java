package com.annotation.services;

import java.io.IOException;
import java.util.List;

import com.annotation.dto.DocumentDTO;
import com.annotation.entities.Annotation;
import com.annotation.entities.Document;
import com.annotation.services.exceptions.DocumentDoesNotExistException;

public interface DocumentService {

	String readFileAsString(String uri) throws IOException;
	
	/**
	 * Deletes the document with the associated id
	 * 
	 * @param id the id to delete
	 */
	void deleteDocument(Long id)  throws IllegalArgumentException;
	
	//TODO
	void addDocument(  Document document,Long idCollection);
	
	
	/**
	 * Retrieves the documents of a specific collection
	 * 
	 * @param collectionId the id of the collection to load
	 * @return the list of documents
	 */
	List<Document> getDocumentsByCollection(Long collectionId);

	/**
	 * Retrieves the text document by the given id
	 *  
	 * @param documentId the id of the document
	 * @return text file of the document
	 */
	Document getDocumentById(Long documentId);

	/**
	 * Modifies an existing document passing a new document with the same id
	 * 
	 * @param document the new document with the same id
	 * @throws DocumentDoesNotExistException if the id does not match any document
	 */
	void modifyDocument(DocumentDTO document) throws DocumentDoesNotExistException;

	
}
