package com.annotation.services;

import java.util.List;

import com.annotation.entities.User;
import com.annotation.services.exceptions.UserAlreadyExistException;
import com.annotation.services.exceptions.UserDoesNotExistsException;

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
	 * @throws UserAlreadyExistException 
	 */
	public void addUser(User user) throws UserAlreadyExistException;
	
	/**
	 * Deletes the user of the app
	 * 
	 * @param id, identification of the user
	 * @throws UserDoesNotExistsException 
	 */
	public void deleteUser(Long id) throws UserDoesNotExistsException;
	
	/**
	 * Finds a user with a specific id
	 * 
	 * @param id to find
	 * @return User that matches the id
	 */
	public User getUserByUsername(String username);
	
	/**
	 * Returns the user with the specified id
	 * 
	 * @param id
	 * @return the user, or in case it does not exist null
	 * @throws UserDoesNotExistsException 
	 */
	public User getUserById(Long id) throws UserDoesNotExistsException;
		
	/**
	 * Logs the user with the specified password
	 * 
	 * @param username the user to log
	 * @param password the password entered
	 * @return True if the login was succesful, False if it failed
	 */
	public boolean login(String username,String password);


	
	public void deleteUser(String username) throws UserDoesNotExistsException;

	void updateUser(User user) throws UserDoesNotExistsException; //TODO:
	
}
