package com.annotation.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import com.annotation.entities.Document;
import com.annotation.entities.DocumentCollection;
import com.annotation.entities.TagGroup;
import com.annotation.entities.User;
import com.annotation.services.exceptions.CollectionAlreadyExistsException;
import com.annotation.services.exceptions.UserAlreadyExistException;
import com.annotation.services.exceptions.UserDataException;
import com.annotation.services.exceptions.UserDoesNotExistsException;


@Service
@Transactional
public class InsertDatabase implements ApplicationRunner{

	
	
	@Autowired
	UsersService usersService;
	
	@Autowired
	CollectionService collectionService;
	
	@Autowired
	DocumentService documentService;

	@Autowired
	TagGroupService tagGroupService;
	
	private void resetDb() throws UserAlreadyExistException, UserDataException, UserDoesNotExistsException, CollectionAlreadyExistsException {
		
		

		usersService.addUser( new User("juan","ROLE_USER","123"));
		usersService.addUser( new User("pepe", "ROLE_USER", "123"));
		usersService.addUser( new User("admin", "ROLE_ADMIN", "admin"));

		usersService.addUser( new User("sara", "ROLE_USER", "123"));

		DocumentCollection col1 = new DocumentCollection("Reddit Comments Collection","Compilation of");
		DocumentCollection col2 = new DocumentCollection("Colección 2","Descripción de la colección 2");
		
		
		collectionService.addCollection(col1);
		collectionService.addCollection(col2);
		
		ArrayList<Long> usersIds = new ArrayList<Long>();
		usersIds.add( usersService.getUserByUsername("juan").getId());
		usersIds.add( usersService.getUserByUsername("admin").getId());
		usersIds.add( usersService.getUserByUsername("sara").getId());
		usersIds.add( usersService.getUserByUsername("pepe").getId());
		
		usersService.addUsersToCollection(col1,usersIds);
		
		usersIds.remove(0);
		usersIds.remove(0);
		
		usersService.addUsersToCollection(col2,usersIds);
		
		Document doc1 = new Document("Doc1","myuri1.com");
		Document doc2 = new Document("Doc2","myuri2.com");
		
		
		List<DocumentCollection>listaColeciones =collectionService.getCollections();
		documentService.addDocument(doc1,listaColeciones.get(0).getId() );
		documentService.addDocument(doc2,listaColeciones.get(0).getId() );
		documentService.addDocument(doc1,listaColeciones.get(1).getId() );
		
		TagGroup tg1 = new TagGroup("APA group","More than 25,000 authoritative entries across 90 subfields of psychology.");
		TagGroup tg2 = new TagGroup("Custom tag group","Customized Tag Group by Admin");
		
		tagGroupService.add(tg1);
		tagGroupService.add(tg2);
		
		
		System.out.println("========== DATA LOADED ==========");		

		
		
		
		
		
	}


	@Override
	public void run(ApplicationArguments args) throws Exception {
			this.resetDb();
		
	}
	
}
