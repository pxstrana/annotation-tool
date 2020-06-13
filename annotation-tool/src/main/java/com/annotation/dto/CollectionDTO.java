package com.annotation.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectionDTO {

	
	private String name;
	private String description;
	
	public CollectionDTO(String name, String description) {
		this.name=name;
		this.description=description;
	}
}
