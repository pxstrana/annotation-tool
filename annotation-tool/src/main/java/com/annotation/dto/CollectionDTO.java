package com.annotation.dto;


import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectionDTO {

	private Long id;
	private String name;
	private String description;
	private ArrayList<Long> usersIds= new ArrayList<Long>();
	
	public CollectionDTO() {
	}
	
	
	public CollectionDTO(String name, String description) {
		this.name=name;
		this.description=description;
	}
}
