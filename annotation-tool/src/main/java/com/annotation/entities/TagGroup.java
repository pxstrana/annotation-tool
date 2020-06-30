package com.annotation.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TagGroup {

	@Id
	@GeneratedValue
	private long id;
	private String name;
	private String description;
	
	@OneToMany(mappedBy = "tagGroup")
	private Set<Tag> tags = new HashSet<Tag>();
	
	public TagGroup() {
	}
}
