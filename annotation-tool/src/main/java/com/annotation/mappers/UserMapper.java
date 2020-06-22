package com.annotation.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.annotation.dto.UserDTO;
import com.annotation.entities.User;

public class UserMapper {

	
	
	public static ArrayList<UserDTO> userToDTO(List<User> users ) {
		ArrayList<UserDTO> a=  (ArrayList<UserDTO>) users.stream()
				.map(userToDTO).collect(Collectors.<UserDTO> toList());
		
		return a;
	}
	
	
	
	
	
	/**
	 * TODO: extract
	 * Transform user to userDTO
	 */
	static Function<User, UserDTO> userToDTO
    = new Function<User, UserDTO>() {

		@Override
		public UserDTO apply(User t) {
			UserDTO dto = new UserDTO(t.getUsername(),t.getRole());
			dto.setId(t.getId());
			return dto;
		}
		
		
	};
	
}
