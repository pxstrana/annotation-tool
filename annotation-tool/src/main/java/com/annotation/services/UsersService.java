package com.annotation.services;

import java.util.List;

import com.annotation.entities.User;
import com.annotation.services.exceptions.UserAlreadyExistException;

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
	 */
	public void deleteUser(Long id);
	
	/**
	 * Find a user with a specific id
	 * @param id
	 * @return User that matches the id
	 */
	public User getUser(Long id);
	
}
