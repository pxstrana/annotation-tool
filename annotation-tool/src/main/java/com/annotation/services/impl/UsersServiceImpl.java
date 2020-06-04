package com.annotation.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.annotation.entities.User;
import com.annotation.repositories.UsersRepository;
import com.annotation.services.UsersService;
import com.annotation.services.exceptions.UserAlreadyExistException;
import com.annotation.services.exceptions.UserDoesNotExistsException;

import io.jsonwebtoken.lang.Assert;

@Service
public class UsersServiceImpl implements UsersService{

	@Autowired
	private UsersRepository usersRepo;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	 
	@Override
	public List<User> getUsers() {
			
		List<User> users = new ArrayList<>();
		usersRepo.findAll().forEach(users::add);
        return users;
	}

	/**
	 * It adds user using the repository only if this username it is not repeated
	 * It also encrypts the password 
	 */
	@Override
	public void addUser(User user) throws UserAlreadyExistException {
		if(usersRepo.findByUsername(user.getUsername())!= null) {
			throw new UserAlreadyExistException("This user already exists");
		}
		
		if(user.getPassword()!=null && user.getUsername().length()!=0) {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			usersRepo.save(user);
		}else {
			throw new RuntimeException("Error in the username");
		}
		
	}

	@Override
	public void deleteUser(Long id) throws UserDoesNotExistsException {
		if(usersRepo.existsById(id)) {
			usersRepo.deleteById(id);
		}
		else {
			throw new UserDoesNotExistsException("User with this id does not exists");
		}
		
	}

	@Override
	public User getUserByUsername(String username) {
		return usersRepo.findByUsername(username);
	}

	@Override
	public User getUserById(Long id) throws UserDoesNotExistsException {
		return usersRepo.findById(id).orElseThrow(()-> new UserDoesNotExistsException("This user does not exists"));
	}

	@Override
	public void updateUser(User user) throws UserDoesNotExistsException {
		User dbUser=getUserById(user.getId());
		if(dbUser==null) {
			throw new UserDoesNotExistsException("This user does not exists");
		}
		dbUser.setRole(user.getRole());
		usersRepo.save(dbUser);
		
		
	}

	@Override
	public boolean login(String username, String password) {
		User userDb = getUserByUsername(username);
		
		return userDb==null ? false :userDb.getPassword().equals(password);
		
		
	}

}
