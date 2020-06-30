package com.annotation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentDTO {

	private Long id;
	private String uri;
	private String name;
	private String docAnnotation;
	private String description;
}
