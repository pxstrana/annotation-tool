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
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		usersRepo.save(user);
		
	}

	@Override
	public void deleteUser(Long id) {
		usersRepo.deleteById(id);
		
	}

	@Override
	public User getUserByUsername(String username) {
		return usersRepo.findByUsername(username);
	}

	@Override
	public User getUserById(Long id) {
		return usersRepo.findById(id).orElseThrow();
	}

	@Override
	public void updateUser(User user) {
		User dbUser=getUserById(user.getId());
		dbUser.setRole(user.getRole());
		usersRepo.save(dbUser);
		
		
	}

}
