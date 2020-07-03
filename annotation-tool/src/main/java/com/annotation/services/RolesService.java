package com.annotation.services;

import org.springframework.stereotype.Service;

@Service
public class RolesService {

    static String[] roles = {"ROLE_USER", "ROLE_ADMIN"};

    public static String[] getRoles() {
        return roles;
    }
    
    
    public static String getAdminRole() {
    	return roles[1];
    }
    
    public static String getUserRole() {
    	return roles[0];
    }
    
    

}
