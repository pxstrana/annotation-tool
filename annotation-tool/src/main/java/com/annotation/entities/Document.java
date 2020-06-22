package com.annotation.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Document {

	@Id
	@GeneratedValue
	private long id;
	@Column(unique = true)
	private String uri;
	private String nombre;
	private String docAnnotation;//TODO: debería de ser una relación de 
	
	@ManyToOne
	@JsonIgnore
	private DocumentCollection collection;
	
	public Document() {
		
	}
	
	public Document(String nombre,String uri) {
		super();
		this.nombre= nombre;
		this.uri = uri;
	}

	@Override
	public String toString() {
		return "Document [id=" + id + ", uri=" + uri + ", nombre=" + nombre + ", docAnnotation=" + docAnnotation
				+ ", collection=" + collection + "]";
	}
	
	

	
}
