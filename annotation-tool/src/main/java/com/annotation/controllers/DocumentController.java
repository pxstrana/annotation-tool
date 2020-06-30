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
import org.apache.tomcat.util.http.fileupload.util.Streams;
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
	public ResponseEntity<List<Document>> getDocuments(@PathVariable(name="id") Long collectionId) {
		return new ResponseEntity<List<Document>>(documentService.getDocumentsByCollection(collectionId), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<StreamingResponseBody> getDocumentbyId(@PathVariable(name="id") Long documentId,final HttpServletResponse response){
		
		try {
		Document doc=documentService.getDocumentById(documentId);
		response.setContentType("text/plain");
		StreamingResponseBody stream = out -> {
            // String home = System.getProperty("user.dir");
             File documentContent = new File(doc.getUri());//new File(home + File.separator + "Documents" + File.separator + "sample");
            
             InputStream inputStream= new FileInputStream(documentContent);
             
             OutputStream  writer = response.getOutputStream();
             IOUtils.copy(inputStream, writer);
             
		};
		return new ResponseEntity<StreamingResponseBody>(stream, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<StreamingResponseBody>(HttpStatus.BAD_REQUEST);
		}
		
       
		
	}
	
	
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
	
	
	
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<String> deleteDocument(@PathVariable(name="id") Long documentId) {
		try {
			documentService.deleteDocument(documentId);
			return new ResponseEntity<String>(HttpStatus.OK);
		}catch ( IllegalArgumentException e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/upload/{id}")
	public ResponseEntity<String> uploadDocument(HttpServletRequest request, @PathVariable(name="id") Long collectionId)  {
		
		try {
			
			 fileServerUpload(request,collectionId);
			
		
		}catch (Exception e) {//FileUploadException | IOException
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
		
	}
	
	
	

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
             } else {
                 //process form fields
                 String formFieldValue = Streams.asString(stream);
                 if(formFieldValue!=null)  {System.out.println(formFieldValue);}
             }
         }
		 return name;
	}
	
	private void unzip(InputStream stream, String destDir,Long idCollection) {
        File dir = new File(destDir);
        System.out.println(destDir);
        // create output directory if it doesn't exist
        if(!dir.exists()) dir.mkdirs();
        //buffer for read and write data to file
        byte[] buffer = new byte[1024];
        try {
            ZipInputStream zis = new ZipInputStream(stream);
            ZipEntry ze = zis.getNextEntry();
            while(ze != null){
                String fileName = ze.getName();
                File newFile = new File(destDir + File.separator + fileName);
                System.out.println("Unzipping to "+newFile.getAbsolutePath());
                documentService.addDocument(new Document(fileName, newFile.getAbsolutePath()), idCollection);
                //create directories for sub directories in zip
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
                }
                fos.close();
                //close this ZipEntry
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            //close last ZipEntry
            zis.closeEntry();
            zis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
	
	private String pathGen(String name,Long collectionId) {
		new File(System.getProperty("user.dir")+File.separator+"Documents"+File.separator+collectionId).mkdir();
		return System.getProperty("user.dir")+File.separator+"Documents"+File.separator+collectionId+File.separator+name;
	}
	private String zipPathGen(String name,Long collectionId) {
		return System.getProperty("user.dir")+File.separator+"Documents"+File.separator+collectionId+File.separator+name;
	}
	

}
