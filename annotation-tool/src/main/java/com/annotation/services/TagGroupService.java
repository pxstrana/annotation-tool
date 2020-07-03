package com.annotation.services;

import java.util.List;

import com.annotation.entities.TagGroup;

public interface TagGroupService {

	/**
	 * Returns all the TagGroups of the system
	 * 
	 * @return List of TagGroups
	 */
	List<TagGroup> getAll();

	/**
	 * Adds the tagGroup to the system
	 * 
	 * @param tagGroup the group to add
	 */
	Long add(TagGroup tagGroup);

	/**
	 * Deletes the specified tag group with the same id
	 * 
	 * @param id the identifier
	 */
	void delete(Long id);

	

}
