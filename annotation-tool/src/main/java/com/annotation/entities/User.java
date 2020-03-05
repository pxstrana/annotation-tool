package com.annotation.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * User entity class with the main characteristics of a standard user
 * 
 * @author Luis
 *
 */
@Getter
@Setter
@ToString
@Entity(name="appUser")
public class User {

	@Id
    @GeneratedValue
    private long id;
	@Column(unique = true)
    private String username;
    private String role;
    private String password;
    @Transient
    private String passwordConfirm;
    
    public User() {
	}
    
    public User(String name) {
    	super();
    	this.username=name;
    }
    public User(String username, String role, String password) {
		super();
		this.username = username;
		this.role = role;
		this.password = password;
	}
    public User(long id, String username, String role, String password) {
		super();
		this.id = id;
		this.username = username;
		this.role = role;
		this.password = password;
	}
    
    /**
     * Compares user with an object and only return true if its an User 
     * with the same username.
     */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
		return Objects.equals(username, user.username);
	}
    /**
     * Creates a hashcode based on the username
     */
	 @Override
	    public int hashCode() {
	        return Objects.hash(username);
	    }

	

	

}