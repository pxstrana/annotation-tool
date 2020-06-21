package com.annotation.services;

import java.io.IOException;
import java.util.List;

import com.annotation.entities.Document;

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

	Document getDocumentById(Long documentId);
}
