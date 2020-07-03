package com.annotation.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.annotation.entities.Tag;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long>{

	@Query("SELECT t FROM Tag t WHERE t.tagGroup.id = (?1)")
	List<Tag> getTagsOfGroup(Long id);

}
