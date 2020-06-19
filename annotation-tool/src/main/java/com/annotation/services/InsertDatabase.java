package com.annotation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.annotation.entities.Document;
import com.annotation.entities.DocumentCollection;
import com.annotation.entities.User;
import com.annotation.repositories.CollectionRepository;
import com.annotation.repositories.DocumentRepository;
import com.annotation.repositories.UsersRepository;


@Component
public class InsertDatabase implements ApplicationRunner{

	
	@Autowired
	UsersRepository userRepo;
	
	@Autowired
	CollectionRepository collectionRepo;
	
	@Autowired
	DocumentRepository documentRepo;
	
	private void resetDb() {
		
		User user1 = new User("pepe", "ROLE_USER", "123");
		User user2 = new User("admin", "ROLE_ADMIN", "admin");

		User user3 = new User("sara", "ROLE_USER", "123");

		DocumentCollection col1 = new DocumentCollection("Reddit Comments Collection","Compilation of");
		DocumentCollection col2 = new DocumentCollection("Colección 2","Descripción de la colección 2");
		
		Document doc1 = new Document("Doc1","myuri1.com");
		Document doc2 = new Document("Doc2","myuri2.com");
		Document doc3 = new Document("Doc3","myuri3.com");
		
		//Actions
		
		col1.addDocument(doc1);
		col1.addDocument(doc2);
		
		col2.addDocument(doc3);
		col2.addDocument(doc1);
	
		
		
		user1.addCollection(col1);
		user2.addCollection(col2);
		
		user3.addCollection(col1);
		user3.addCollection(col2);
		
		//Saving...
		
		
		
		collectionRepo.save(col1);
		collectionRepo.save(col2);
		System.out.println("guardadas colecciones");
		
		documentRepo.save(doc1);
		documentRepo.save(doc2);
		documentRepo.save(doc3);
		
		
		userRepo.save(user1);
		userRepo.save(user2);
		userRepo.save(user3);	
		System.out.println("guardados Usuarios");
		
		
		
	}


	@Override
	public void run(ApplicationArguments args) throws Exception {
			this.resetDb();
		
	}
	
}
