package com.annotation.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class DocumentCollection {

	@Id
	@GeneratedValue
	private long id;
	@Column(unique=true)
	private String name;
	private String description;
	
	@ManyToMany (mappedBy = "collections", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JsonIgnore
	Set<User> usersAllowed =  new HashSet<User>() ;
	
	@OneToMany(mappedBy="collection")
	private Set<Document> documents = new HashSet<Document>();
	
	public DocumentCollection() {
		 
	}

	public DocumentCollection(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	
	
	public void addDocument(Document document) {
		this.documents.add(document);
		document.setCollection(this);
	}
	
	public void removeDocument(Document document) {
		this.documents.remove(document);
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentCollection other = (DocumentCollection) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}



	
	
	
}
