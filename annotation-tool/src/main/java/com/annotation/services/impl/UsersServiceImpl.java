package com.annotation.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.annotation.entities.User;
import com.annotation.repositories.UsersRepository;
import com.annotation.services.UsersService;
import com.annotation.services.exceptions.UserAlreadyExistException;

public class UsersServiceImpl implements UsersService{

	@Autowired
	private UsersRepository usersRepo;
	
	@Override
	public List<User> getUsers() {
			
		List<User> users = new ArrayList<>();
		usersRepo.findAll().forEach(users::add);
        return users;
	}

	@Override
	public void addUser(User user) throws UserAlreadyExistException {
		if(usersRepo.findByUsername(user.getUsername())!= null) {
			throw new UserAlreadyExistException("This user already exists");
		}
		usersRepo.save(user);
		
	}

	@Override
	public void deleteUser(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User getUser(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
