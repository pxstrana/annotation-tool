package com.annotation.services;

import java.util.ArrayList;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import com.annotation.entities.DocumentCollection;
import com.annotation.entities.TagGroup;
import com.annotation.entities.User;
import com.annotation.services.exceptions.AlreadyExistsException;
import com.annotation.services.exceptions.UserDataException;
import com.annotation.services.exceptions.UserDoesNotExistException;

/**
 * Insert data in the database
 * 
 * @author Luis Pastrana García
 *
 */
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
	
	/**
	 * Insert mockup data
	 * 
	 * @throws UserDataException Exception in the user data
	 * @throws UserDoesNotExistException The user does not exist
	 * @throws AlreadyExistsException The entity already exists
	 */
	private void resetDb() throws UserDataException, UserDoesNotExistException, AlreadyExistsException {
		
		

		usersService.addUser( new User("user1","ROLE_USER","123"));
		usersService.addUser( new User("pepe", "ROLE_USER", "123"));
		usersService.addUser( new User("admin", "ROLE_ADMIN", "admin"));

		usersService.addUser( new User("sara", "ROLE_USER", "123"));

		DocumentCollection col1 = new DocumentCollection("Reddit Comments Collection","Comments of the Reddit Forums");
		DocumentCollection col2 = new DocumentCollection("Other collection","Description of the other collection");
		
		
		collectionService.addCollection(col1);
		collectionService.addCollection(col2);
		
		ArrayList<Long> usersIds = new ArrayList<Long>();
		usersIds.add( usersService.getUserByUsername("user1").getId());
		usersIds.add( usersService.getUserByUsername("admin").getId());
		usersIds.add( usersService.getUserByUsername("sara").getId());
		usersIds.add( usersService.getUserByUsername("pepe").getId());
		
		usersService.addUsersToCollection(col1,usersIds);
		
		usersIds.remove(0);
		usersIds.remove(0);
		
		usersService.addUsersToCollection(col2,usersIds);
		
		
		TagGroup tg1 = new TagGroup("APA group","More than 25,000 authoritative entries across 90 subfields of psychology.");
		TagGroup tg2 = new TagGroup("Custom tag group","Customized Tag Group by Admin");
		
		tagGroupService.add(tg1);
		tagGroupService.add(tg2);
		
		
		System.out.println("========== DATA LOADED ==========");		

	
		
	}
	
	/**
	 * Insert only an administrator
	 * 
	 * @throws UserDataException
	 * @throws AlreadyExistsException
	 */
	@SuppressWarnings("unused") 
	private void onlyAdmin() throws UserDataException, AlreadyExistsException  {
		usersService.addUser( new User("admin", "ROLE_ADMIN", "admin"));
		
	}


	@Override
	public void run(ApplicationArguments args) throws Exception {
			this.resetDb();
		
	}
	
}
