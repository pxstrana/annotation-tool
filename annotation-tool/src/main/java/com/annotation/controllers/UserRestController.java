package com.annotation.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.annotation.dto.UserDTO;
import com.annotation.entities.User;
import com.annotation.mappers.UserMapper;
import com.annotation.services.UsersService;
import com.annotation.services.exceptions.UserAlreadyExistException;
import com.annotation.services.exceptions.UserDataException;
import com.annotation.services.exceptions.UserDoesNotExistsException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200", "http://localhost:8081" })
@RestController
public class UserRestController {

	
	@Autowired
	UsersService usersService;

	@PostMapping("login")
	public ResponseEntity<UserDTO> login(@RequestBody UserDTO user) {
		if (usersService.login(user.getUsername(), user.getPassword())) {

			
			String token = getJWTToken(user.getUsername());
			UserDTO userDTO = new UserDTO();
			userDTO.setUsername(user.getUsername());
			userDTO.setRole(usersService.getUserByUsername(user.getUsername()).getRole());
			userDTO.setToken(token);
			return new ResponseEntity<UserDTO>(userDTO,HttpStatus.OK);
		}
		return new ResponseEntity<UserDTO>(HttpStatus.UNAUTHORIZED);
	}
	
	/**
	 * Retrieve user list from db and sends userDTO
	 * 
	 * @return list of userDTO
	 */
	@GetMapping("user/list")
	public ResponseEntity<ArrayList<UserDTO>> listAll() {
		
		ArrayList<UserDTO> users = UserMapper.userToDTO(usersService.getUsers());
		return new ResponseEntity<ArrayList<UserDTO>>(users,HttpStatus.OK);
	}
	
	
	
	@PostMapping(value = "/user/add")
	public ResponseEntity<String> addUser( @RequestBody UserDTO user) {
		
		User u= new User(user.getUsername(), user.getRole(), user.getPassword());// TODO cambio de userDTO -> user
		
		try {
			usersService.addUser(u);
			
		} catch (UserAlreadyExistException e) { 
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		} catch (UserDataException e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	

	@DeleteMapping("/user/delete/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id) {
		try {
			usersService.deleteUser(id);
		} catch (UserDoesNotExistsException e) {
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	
	
	/**
	 * Creates a token for each different username
	 * 
	 * @param username
	 * @return token to that user
	 */
	private String getJWTToken(String username) {
		String secretKey = "mySecretKey";
	
		String role = usersService.getUserByUsername(username).getRole();
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(role);

		String token = Jwts.builder().setId("annotationTool").setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

		return "Bearer " + token;
	}
}
