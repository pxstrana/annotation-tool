package com.annotation.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Tag {
	
	@Id
	@GeneratedValue
	private long id;
	private String name;
	private String description;
	
	@ManyToOne
	@JsonIgnore
	private TagGroup tagGroup;
	
	
	public Tag() {
	}


	public Tag(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	
	
}
