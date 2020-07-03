package com.annotation.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annotation.entities.Tag;
import com.annotation.entities.TagGroup;
import com.annotation.repositories.TagGroupRepository;
import com.annotation.repositories.TagRepository;
import com.annotation.services.TagService;

@Service
public class TagServiceImpl implements TagService{

	@Autowired
	TagRepository tagRepo;
	
	@Autowired
	TagGroupRepository groupRepo;
	
	@Override
	public void addTag(Tag tag, Long id) {
		
		TagGroup tagGroup =  groupRepo.findById(id).get();
		tagGroup.addTag(tag);
		tagRepo.save(tag);
		
	}
	
	@Override
	public List<Tag> getTagsOfGroup(Long id) {
		return tagRepo.getTagsOfGroup(id);
	}

	@Override
	public void deleteTag(Long id) {
		tagRepo.deleteById(id);
		
	}

}
