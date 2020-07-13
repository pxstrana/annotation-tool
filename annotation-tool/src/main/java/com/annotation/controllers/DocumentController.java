package com.annotation.controllers;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
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
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.annotation.dto.DocumentDTO;
import com.annotation.entities.Document;
import com.annotation.services.DocumentService;

/**
 * Controller of the document requests
 * 
 * @author Luis Pastrana
 *
 */
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200", "http://localhost:8081" })
@RestController
@RequestMapping("/document")
public class DocumentController {


	@Autowired
	DocumentService documentService;
	
	/**
	 * Retrieve all the documents of one collection
	 * 
	 * @param collectionId the id of the collection
	 * @return List of documents of the collection
	 */
	@GetMapping("/collection/{id}")
	public ResponseEntity<List<Document>> getDocuments(@PathVariable(name="id") Long collectionId) {
		return new ResponseEntity<List<Document>>(documentService.getDocumentsByCollection(collectionId), HttpStatus.OK);
	}
	
	/**
	 * Returns the document with the given id
	 * @param documentId the identifier of the document
	 * @param response the response of the petition
	 * @return Content of the document
	 */
	@GetMapping("/{id}")
	public ResponseEntity<StreamingResponseBody> getDocumentbyId(@PathVariable(name="id") Long documentId,final HttpServletResponse response){
		
		try {
		Document doc=documentService.getDocumentById(documentId);
		response.setContentType("text/plain");
		StreamingResponseBody stream = out -> {
            
             File documentContent = new File(doc.getUri()); 
             InputStream inputStream= new FileInputStream(documentContent);      
             OutputStream  writer = response.getOutputStream(); 
             IOUtils.copy(inputStream, writer);
             
             inputStream.close();
             writer.close();
             
		};
		return new ResponseEntity<StreamingResponseBody>(stream, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<StreamingResponseBody>( HttpStatus.BAD_REQUEST);
		}
		
       
		
	}
	
	/**
	 * Modifies an existing document with the given one.
	 * @param document the new document 
	 * @return HttpStatus.OK if it is correct, HttpStatus.BAD_REQUEST if it is not
	 */
	@PostMapping("/modify")
	public ResponseEntity<String> modifyDocument( @RequestBody DocumentDTO document){
		try {
			
			documentService.modifyDocument(document);
		}catch( Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>(HttpStatus.OK);
		
	}
	
	
	
	/**
	 * Deletes document by a given id
	 * @param documentId the identifier of the document
	 * @return HttpStatus.OK if it is correct, HttpStatus.BAD_REQUEST if it is not
	 */
	@DeleteMapping("delete/{id}")
	public ResponseEntity<String> deleteDocument(@PathVariable(name="id") Long documentId) {
		try {
			documentService.deleteDocument(documentId);
			
		}catch ( Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	/**
	 * Uploads a new document
	 * 
	 * @param request the request of the petition
	 * @param collectionId the id of the collection in which is going to be added the document
	 * @return HttpStatus.OK if it is correct, HttpStatus.BAD_REQUEST if it is not
	 */
	@PostMapping("/upload/{id}")
	public ResponseEntity<String> uploadDocument(HttpServletRequest request, @PathVariable(name="id") Long collectionId)  {
		
		try {
			
			 fileServerUpload(request,collectionId);
			
		
		}catch (Exception e) {//FileUploadException | IOException
			e.printStackTrace();
			return new ResponseEntity<String>(e.getStackTrace().toString(),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
		
	}
	
	
	
	/**
	 * Uploads the document into the server file system
	 * @param request of the petition
	 * @param collectionId the identifier of the collection
	 * @return name of the file uploaded
	 * @throws FileUploadException fails while uploading the file
	 * @throws IOException exception occurred
	 * @throws FileNotFoundException 
	 */
	private String fileServerUpload(HttpServletRequest request, Long collectionId)
			throws FileUploadException, IOException, FileNotFoundException {
		
		ServletFileUpload upload = new ServletFileUpload();
		FileItemIterator iterStream = upload.getItemIterator(request);
		String name = null; 
		 while (iterStream.hasNext()) {
             FileItemStream item = iterStream.next();
             InputStream stream = item.openStream();
             
             if (!item.isFormField()) {
            	 name = item.getName();
            	 System.out.println(name);
            	 String[] splittedName = name.split("\\.");
            	 System.out.println(splittedName[0]);
            	 
            	 if(splittedName[splittedName.length-1].equals("zip")){
            		 System.out.println("uploading ZIP");
            		 
            		 unzip(stream,zipPathGen(splittedName[0],collectionId),collectionId);
            		 stream.close();
            	 }else {
            		 
            		 
	            	 OutputStream out = new FileOutputStream(pathGen(name, collectionId));
	                 IOUtils.copy(stream, out);
	                 stream.close();
	                 out.close();
	                 documentService.addDocument(new Document(name,pathGen(name, collectionId)), collectionId);
            	 }
             } 
         }
		 return name;
	}
	
	/**
	 * Unzips compressed files
	 * @param stream of the compressed file
	 * @param destDir path where it will be stored
	 * @param idCollection collection identifier to create a folder with its id.
	 */
	private void unzip(InputStream stream, String destDir,Long idCollection) {
        File dir = new File(destDir);
        System.out.println(destDir);
        if(!dir.exists()) dir.mkdirs();
        byte[] buffer = new byte[1024];
        try {
            ZipInputStream zis = new ZipInputStream(stream);
            ZipEntry ze = zis.getNextEntry();
            while(ze != null){
                String fileName = ze.getName();
                File newFile = new File(destDir + File.separator + fileName);
                System.out.println("Unzipping to "+newFile.getAbsolutePath());
                documentService.addDocument(new Document(fileName, newFile.getAbsolutePath()), idCollection);
 
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
                }
                fos.close();
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
	
	/**
	 * Generates a path in the server
	 * @param name the name of the file
	 * @param collectionId the collection identifier
	 * @return Path 
	 */
	private String pathGen(String name,Long collectionId) {
		new File(System.getProperty("user.dir")+File.separator+"Documents"+File.separator+collectionId).mkdir();
		return System.getProperty("user.dir")+File.separator+"Documents"+File.separator+collectionId+File.separator+name;
	}
	/**
	 * Generates a path to store compressed files
	 * @param name the name of the file
	 * @param collectionId the collection identifier
	 * @return Path
	 */
	private String zipPathGen(String name,Long collectionId) {
		return System.getProperty("user.dir")+File.separator+"Documents"+File.separator+collectionId+File.separator+name;
	}
	

}
