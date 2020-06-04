package com.annotation.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.annotation.dto.UserDTO;
import com.annotation.entities.User;
import com.annotation.services.UsersService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200", "http://localhost:8081" })
@RestController
public class UserRestController {

	
	private static final boolean DEBUG= true;
	
	@Autowired
	UsersService usersService;

	@PostMapping("login")
	public UserDTO login(@RequestBody UserDTO user) {
		if (usersService.login(user.getUsername(), user.getPassword()) || DEBUG == true) {

			String token = getJWTToken(user.getUsername());
			UserDTO userDTO = new UserDTO();
			userDTO.setUsername(user.getUsername());
			userDTO.setToken(token);
			return userDTO;
		}
		return null;
	}
	
	/**
	 * Retrieve user list from db and sends userDTO
	 * 
	 * @return list of userDTO
	 */
	@GetMapping("user/list")
	public ArrayList<UserDTO> listAll() {

		ArrayList<UserDTO> a=  (ArrayList<UserDTO>) usersService.getUsers().stream()
				.map(userToDTO).collect(Collectors.<UserDTO> toList());
		
		return a;
	}
	
	/**
	 * Transform user to userDTO
	 */
	Function<User, UserDTO> userToDTO
    = new Function<User, UserDTO>() {

		@Override
		public UserDTO apply(User t) {
			UserDTO dto = new UserDTO(t.getUsername(),t.getRole());
			return dto;
		}
		
		
	};
	
	
	// TODO cambio de userDTO -> user
	@PostMapping(value = "/user/add")
	public HashMap<String, Object> addUser( @RequestBody UserDTO user) {

		HashMap<String, Object> map = new HashMap<>();
		User u= new User(user.getUsername(), user.getRole(), user.getPassword());
		
		try {
			usersService.addUser(u);
			
		} catch (Exception e) { //TODO  UserAlreadyExistException
			map.put("error", true);
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * Creates a token for each different username
	 * 
	 * @param username
	 * @return token to that user
	 */
	private String getJWTToken(String username) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

		String token = Jwts.builder().setId("annotationTool").setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

		return "Bearer " + token;
	}
}
