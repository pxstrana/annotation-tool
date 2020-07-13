package com.annotation.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.annotation.entities.Tag;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long>{

	/**
	 * Returns the Tags of a tag group
	 * 
	 * @param id the id of the tag group
	 * @return List of tags
	 */
	@Query("SELECT t FROM Tag t WHERE t.tagGroup.id = (?1)")
	List<Tag> getTagsOfGroup(Long id);

}
