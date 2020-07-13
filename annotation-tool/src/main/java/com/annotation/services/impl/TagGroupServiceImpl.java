package com.annotation.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annotation.entities.TagGroup;
import com.annotation.repositories.TagGroupRepository;
import com.annotation.repositories.TagRepository;
import com.annotation.services.TagGroupService;

@Service
public class TagGroupServiceImpl implements TagGroupService{

	@Autowired
	TagGroupRepository tagGroupRepo;
	
	@Autowired
	TagRepository tagRepo;
	
	@Override
	public List<TagGroup> getAll() {
		
		List<TagGroup> groups =  new ArrayList<TagGroup>();
		tagGroupRepo.findAll().forEach(g -> groups.add(g));;
		return groups;
	}

	@Override
	public Long add(TagGroup tagGroup) {
		return tagGroupRepo.save(tagGroup).getId();
		
		
	}

	@Override
	public void delete(Long id) {
		tagGroupRepo.deleteById(id);
		
	}

	
	
	
	

}
