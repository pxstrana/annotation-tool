package com.annotation.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
	private String description;
	
	@OneToMany(mappedBy = "document")
	@JsonIgnore
	private Set<Layer> layers = new HashSet<Layer>();
	
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
		return "Document [id=" + id + ", uri=" + uri + ", nombre=" + nombre +  ", collection=" + collection + "]";
	}

	public void addLayer(Layer layer) {
		this.layers.add(layer);
		layer.setDocument(this);
		
	}
	
	

	
}
