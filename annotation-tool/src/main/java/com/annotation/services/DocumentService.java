package com.annotation.services;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import com.annotation.dto.DocumentDTO;
import com.annotation.entities.Document;

public interface DocumentService {

	/**
	 * Reads the content of the file
	 * @param uri the uri of the file
	 * @return String with the content
	 * @throws IOException in case there are any errors
	 */
	String readFileAsString(String uri) throws IOException;
	
	/**
	 * Deletes the document with the associated id
	 * 
	 * @param id the id to delete
	 * @throws IOException I/O error
	 * @throws NoSuchElementException there is no  document with that id
	 */
	void deleteDocument(Long id)  throws IllegalArgumentException, NoSuchElementException , IOException;
	
	/**
	 * Adds document to a collection
	 * 
	 * @param document the document to add
	 * @param idCollection the id of the collection
	 */
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
	 * @throws NoSuchElementException if the id does not match any document
	 */
	void modifyDocument(DocumentDTO document) throws NoSuchElementException;

	
}
