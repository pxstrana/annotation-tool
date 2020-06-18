package com.annotation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	
	
	private Long id;
	private String username;
    private String role;
    private String token;
    private String password;
    private String passwordConfirm;
    
    public UserDTO() {
	}
    
    public UserDTO(String username,String role) {
    	this.username=username;
    	this.role=role;
    }
}
