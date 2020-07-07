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
	private Long id;
	private String name;
	private String description;
	
	@OneToMany(orphanRemoval=true ,mappedBy = "tagGroup")
	private Set<Tag> tags = new HashSet<Tag>();
	
	public TagGroup() {
	}

	public TagGroup( String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public void addTag(Tag tag) {
		this.tags.add(tag);
		tag.setTagGroup(this);
		
	}
	
	
}
