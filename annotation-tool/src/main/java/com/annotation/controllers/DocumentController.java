package com.annotation.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipOutputStream;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

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
	
	@GetMapping("/{id}")
	public ResponseEntity<StreamingResponseBody> getDocumentbyId(@PathVariable(name="id") Long documentId,final HttpServletResponse response){
		
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
		
	}
	
	@DeleteMapping("delete/{id}")
	public void deleteDocument(@PathVariable(name="id") Long documentId) {
		documentService.deleteDocument(documentId);
	}
	
	@PostMapping("/upload/{id}")
	public void uploadDocument(HttpServletRequest request, @PathVariable(name="id") Long collectionId) throws FileUploadException, IOException {
		//TODO: manejar excepciones y errroes
		String name =  fileServerUpload(request);
		System.out.println(collectionId);
		
		documentService.addDocument(new Document(name,System.getProperty("user.dir")+File.separator+"Documents"+File.separator+name), collectionId);
	}
	
	
	

	private String fileServerUpload(HttpServletRequest request)
			throws FileUploadException, IOException, FileNotFoundException {
		ServletFileUpload upload = new ServletFileUpload();
		FileItemIterator iterStream = upload.getItemIterator(request);
		String name = null; //TODO: si solo es un archivo, si se permiten mas cambiar array 
		 while (iterStream.hasNext()) {
             FileItemStream item = iterStream.next();
             InputStream stream = item.openStream();
             
             if (!item.isFormField()) {
            	  name = item.getName();
            	 OutputStream out = new FileOutputStream(System.getProperty("user.dir")+File.separator+"Documents"+File.separator+name);
                 IOUtils.copy(stream, out);
                 stream.close();
                 out.close();
             } else {
                 //process form fields
                 String formFieldValue = Streams.asString(stream);
                 if(formFieldValue!=null)  {System.out.println(formFieldValue);}
             }
         }
		 return name;
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
//	@PostMapping(value="/load")
//	public String loadDocument(Model model,@RequestParam("uri") String payload) {
//		System.out.println(uriDecoder(payload));
//		try {
//			String texto = documentService.readFileAsString(uriDecoder(payload));
//			model.addAttribute("text",texto);
//		} catch (IOException e) {
//			model.addAttribute(ERROR,true);
//		}
//		
//		return "document/showDoc";
//	}
	
	
	
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
