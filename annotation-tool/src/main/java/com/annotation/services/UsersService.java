package com.annotation.services;

import java.util.List;

import com.annotation.entities.User;
import com.annotation.services.exceptions.UserAlreadyExistException;
import com.annotation.services.exceptions.UserDoesNotExistsException;

/**
 * 
 * Operations in the user logic
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
	 * Add user to the app
	 * @param user entitie to be added
	 * @throws UserAlreadyExistException 
	 */
	public void addUser(User user) throws UserAlreadyExistException;
	
	/**
	 * Delete the user of the app
	 * @param id, identification of the user
	 * @throws UserDoesNotExistsException 
	 */
	public void deleteUser(Long id) throws UserDoesNotExistsException;
	
	/**
	 * Find a user with a specific id
	 * @param id
	 * @return User that matches the id
	 */
	public User getUserByUsername(String username);
	
	/**
	 * Returns the user with the specified id
	 * @param id
	 * @return the user, or in case it does not exist null
	 */
	public User getUserById(Long id);
	
	public void updateUser(User user) throws UserDoesNotExistsException;
	
}
