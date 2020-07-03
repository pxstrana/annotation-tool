package com.annotation.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    
    @JsonIgnore
    private String password;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
    		name = "user_collections",
    		joinColumns = @JoinColumn (name = "user_id"),
    		inverseJoinColumns = @JoinColumn(name = "collection_id"))
    @JsonIgnore
    Set<DocumentCollection> collections = new HashSet<>();
    
    
    
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
     * Adds a document collection to the user and the users updates on the 
     * collection itself.
     * 
     * @param documentCollection
     */
    public void addCollection(DocumentCollection documentCollection) {
    	this.collections.add(documentCollection);
    	documentCollection.getUsersAllowed().add(this);
    }
    
    
    public void removeCollection(DocumentCollection documentCollection) {
    	documentCollection.getUsersAllowed().remove(this);
    	this.collections.remove(documentCollection);
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