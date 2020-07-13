package com.annotation.services;

import org.springframework.stereotype.Service;


@Service
public class RolesService {

    static String[] roles = {"ROLE_USER", "ROLE_ADMIN"};

    /**
     * Return all available roles
     * @return List of Roles
     */
    public static String[] getRoles() {
        return roles;
    }
    
    /**
     * Returns the admin role String
     * @return Admin Role String
     */
    public static String getAdminRole() {
    	return roles[1];
    }
    
    /**
     * Returns the user role String
     * @return User Role String
     */
    public static String getUserRole() {
    	return roles[0];
    }
    
    

}
