package com.annotation.services;

import java.util.List;

import com.annotation.entities.Tag;

public interface TagService {

	/**
	 * Add a tag to the tagGroup with that id
	 * 
	 * @param tag the tag to add
	 * @param id the identifier of the tagGroup
	 */
	void addTag(Tag tag, Long id);

	/**
	 * Deletes the tag with that id
	 * 
	 * @param id the identifier of the tag
	 */
	void deleteTag(Long id);

	/**
	 * Returns the list of tags of a specific tag Group
	 * 
	 * @param id the tag group identifier
	 * @return list of tags 
	 */
	List<Tag> getTagsOfGroup(Long id);
	


}
