package com.annotation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	
	private String username;
    private String role;
    private String password;
    private String passwordConfirm;
}
