package com.annotation.dto;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnnotationDTO {

	private Long id;
	private String tag;
	
	private int startOffset;
	private int endOffset;
	
	private Long layerId;
	
	
}
