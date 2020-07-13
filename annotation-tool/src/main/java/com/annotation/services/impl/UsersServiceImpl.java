package com.annotation.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.annotation.dto.UserDTO;
import com.annotation.entities.DocumentCollection;
import com.annotation.entities.User;
import com.annotation.repositories.UsersRepository;
import com.annotation.services.UsersService;
import com.annotation.services.exceptions.AlreadyExistsException;
import com.annotation.services.exceptions.UserDataException;
import com.annotation.services.exceptions.UserDoesNotExistException;

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

	@Override
	public void addUser(User user) throws AlreadyExistsException, UserDataException {
		if(usersRepo.findByUsername(user.getUsername())!= null) {
			throw new AlreadyExistsException("This user already exists");
		}
		
		if(user.getPassword()!=null && user.getUsername()!=null && user.getUsername().length()!=0) {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			usersRepo.save(user);
		}else {
			throw new UserDataException("User Data is not correct while adding");
		}
		
	}

	@Override
	public void deleteUser(Long id) throws UserDoesNotExistException {
		try {
			User user = usersRepo.findById(id).get();
			user.getCollections().forEach( collection -> collection.getUsersAllowed().remove(user));
			usersRepo.deleteById(id);
		}
		catch(NoSuchElementException e) {
			throw new UserDoesNotExistException("User with this id does not exists");
		}
		
	}

	@Override
	public User getUserByUsername(String username) {
		return usersRepo.findByUsername(username);
	}

	@Override
	public User getUserById(Long id) throws UserDoesNotExistException {
		return usersRepo.findById(id).orElseThrow(()-> new UserDoesNotExistException("This user does not exists"));
	}


	@Override
	public boolean login(String username, String password) {
		User userDb = getUserByUsername(username);
		return userDb==null ? false :bCryptPasswordEncoder.matches(password, userDb.getPassword());
		
		
	}

	@Override
	public void deleteUser(String username) throws UserDoesNotExistException {
		User u=usersRepo.findByUsername(username);
		if(u!=null) {
			deleteUser(u.getId());
		}else {
			throw new UserDoesNotExistException("The user: "+username+" does not exist");
		}
		
	}

	@Override
	public void addUsersToCollection(DocumentCollection collection, ArrayList<Long> usersIds) throws UserDoesNotExistException {
		for (Long id : usersIds) {
			User user =this.getUserById(id);
			user.addCollection(collection);
			usersRepo.save(user);
		}
		
	}

	@Override
	public void modifyUser(UserDTO userDTO) throws NoSuchElementException {
		User user = usersRepo.findById(userDTO.getId()).get();
		if(user == null) throw new NoSuchElementException();
		user.setRole(userDTO.getRole());
		usersRepo.save(user);
		
		
	}

}
