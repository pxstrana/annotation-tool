package com.annotation.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.annotation.dto.CollectionDTO;
import com.annotation.entities.DocumentCollection;

public class CollectionMapper {

	public static CollectionDTO build(DocumentCollection collection) {
		
		return new CollectionDTO(collection.getName(),collection.getDescription());
	}
	
	public static List<CollectionDTO> convertCollectionToDTO(List<DocumentCollection> collections){
		
		return collections.stream().map(CollectionMapper::build).collect(Collectors.<CollectionDTO> toList());
		
	}
	
	
}





