package com.annotation.repositories;

import org.springframework.data.repository.CrudRepository;

import com.annotation.entities.User;

/**
 * Interface to the repository, entitie: User, identificator type: Long
 * @author Luis
 *
 */
public interface UsersRepository extends CrudRepository<User, Long>{

	User findByUsername(String username);

	
}
