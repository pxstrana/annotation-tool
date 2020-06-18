package com.annotation.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.annotation.dto.UserDTO;
import com.annotation.entities.User;
import com.annotation.services.InsertDatabase;
import com.annotation.services.RolesService;
import com.annotation.services.UsersService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200", "http://localhost:8081" })
@RestController
public class UserRestController {

	
	private static final boolean DEBUG= false;
	
	@Autowired
	UsersService usersService;

	@PostMapping("login")
	public Object login(@RequestBody UserDTO user) {
		if (usersService.login(user.getUsername(), user.getPassword()) || DEBUG ) {

			
			String token = getJWTToken(user.getUsername());
			UserDTO userDTO = new UserDTO();
			userDTO.setUsername(user.getUsername());
			userDTO.setRole(usersService.getUserByUsername(user.getUsername()).getRole());
			userDTO.setToken(token);
			return userDTO;
		}
		return new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED);
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
			dto.setId(t.getId());
			return dto;
		}
		
		
	};
	
	
	
	@PostMapping(value = "/user/add")
	public HashMap<String, Object> addUser( @RequestBody UserDTO user) {

		HashMap<String, Object> map = new HashMap<>();
		
		
		User u= new User(user.getUsername(), user.getRole(), user.getPassword());// TODO cambio de userDTO -> user
		
		try {
			usersService.addUser(u);
			
		} catch (Exception e) { //TODO  UserAlreadyExistException
			map.put("error", true);
			e.printStackTrace();
		}
		return map;
	}
	

	@PostMapping("/user/delete")
	public HashMap<String, Object> deleteUser(@RequestBody UserDTO user) {
		HashMap<String, Object> map =new HashMap<String, Object>();
		try {
			usersService.deleteUser(user.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("error", true);
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
	
		String role = usersService.getUserByUsername(username).getRole();
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(role);//TODO cambiar por el rol del usuario

		String token = Jwts.builder().setId("annotationTool").setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

		return "Bearer " + token;
	}
}
