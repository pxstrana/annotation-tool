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
public class Annotation {


	@Id
	@GeneratedValue
	private Long id;
	
	private String tag;
	
	private int startOffset;
	private int endOffset;
	
	@ManyToOne
	@JsonIgnore
	private Layer layer;

	public Annotation() {
	}
	
	public Annotation(String tag, int startOffset, int endOffset) {
		super();
		this.tag = tag;
		this.startOffset = startOffset;
		this.endOffset = endOffset;
	}
	
	
	
	
}
