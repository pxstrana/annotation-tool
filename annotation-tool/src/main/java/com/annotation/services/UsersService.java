package com.annotation.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.annotation.dto.UserDTO;
import com.annotation.entities.DocumentCollection;
import com.annotation.entities.User;
import com.annotation.services.exceptions.AlreadyExistsException;
import com.annotation.services.exceptions.UserDataException;
import com.annotation.services.exceptions.UserDoesNotExistException;

/**
 * 
 * User Service is an interface for all the operations
 * related with the users.
 * 
 * @author Luis
 * @version 0.1
 */
public interface UsersService {
	
	
	/**
	 * @return List of all users of the app
	 */
	public List<User> getUsers();
	
	/**
	 * Adds user to the app
	 * 
	 * @param user entity to be added
	 * @throws UserDataException user data invalid
	 * @throws AlreadyExistsException the user already exists
	 */
	public void addUser(User user) throws  UserDataException, AlreadyExistsException;
	
	/**
	 * Deletes the user of the app
	 * 
	 * @param id, identification of the user
	 * @throws UserDoesNotExistException the user does not exist
	 */
	public void deleteUser(Long id) throws UserDoesNotExistException;
	
	/**
	 * Finds a user with a specific username
	 * 
	 * @param username to find
	 * @return User that matches the username
	 */
	public User getUserByUsername(String username);
	
	/**
	 * Returns the user with the specified id
	 * 
	 * @param id the id of the user
	 * @return the user, or in case it does not exist null
	 * @throws UserDoesNotExistException  there is no user with the given id
	 */
	public User getUserById(Long id) throws UserDoesNotExistException;
		
	/**
	 * Logs the user with the specified password
	 * 
	 * @param username the user to log
	 * @param password the password entered
	 * @return True if the login was succesful, False if it failed
	 */
	public boolean login(String username,String password);


	/**
		Deletes user with the given name
		@deprecated 
		@param username of the user to be deleted
	 */
	public void deleteUser(String username) throws UserDoesNotExistException;

	/**
	 * Allows a list of users into the given collection
	 * 
	 * @param collection the given collection
	 * @param usersIds users ids to be allowed to the collection
	 * @throws UserDoesNotExistException if the user does not exist
	 */
	public void addUsersToCollection(DocumentCollection collection, ArrayList<Long> usersIds) throws UserDoesNotExistException;

	
	/**
	 * Modifies the user with new data
	 * @param userDTO  the user data
	 */
	public void modifyUser(UserDTO userDTO) throws NoSuchElementException;
	
}
