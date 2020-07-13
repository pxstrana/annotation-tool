package com.annotation.repositories;

import org.springframework.data.repository.CrudRepository;
import com.annotation.entities.User;


public interface UsersRepository extends CrudRepository<User, Long>{

	/**
	 * Return the user with that username
	 * 
	 * @param username the username of the user
	 * @return User
	 */
	User findByUsername(String username);

	
}
