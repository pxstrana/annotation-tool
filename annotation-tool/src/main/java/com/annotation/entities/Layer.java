package com.annotation.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Layer {
	
	@Id
	@GeneratedValue
	private long id;
	private String name;
	
	@ManyToOne
	private Document document;
	
	@OneToMany(mappedBy = "layer")
	@JsonIgnore
	private Set<Annotation> annotations;

	
	public Layer() {
	}
	
	
	public void addAnnotation(Annotation annotation) {
		this.annotations.add(annotation);
		annotation.setLayer(this);
		
	}




	public Layer(String name) {
		super();
		this.name = name;
	}
	
	

}
