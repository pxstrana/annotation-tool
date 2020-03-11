package com.annotation.entities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
	private String docAnnotation;

	
	
	
}
