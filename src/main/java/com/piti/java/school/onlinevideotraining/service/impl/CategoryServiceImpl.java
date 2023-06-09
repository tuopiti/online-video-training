package com.piti.java.school.onlinevideotraining.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.piti.java.school.onlinevideotraining.exception.ResourceNotFoundException;
import com.piti.java.school.onlinevideotraining.model.Category;
import com.piti.java.school.onlinevideotraining.repository.CategoryRepository;
import com.piti.java.school.onlinevideotraining.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

	private final CategoryRepository categoryRepository;
	
	@Override
	public Category save(Category entity) {		 
		return categoryRepository.save(entity);
	}

	@Override
	public Category getById(Long id) {
		return categoryRepository.findById(id)
		.orElseThrow(() -> new ResourceNotFoundException("Category", id));
		 
	}

	@Override
	public Category update(Long id, Category categoryUpdate) {
		Category category = getById(id);
		category.setName(categoryUpdate.getName());
		category.setActive(categoryUpdate.getActive());
		return categoryRepository.save(category);
	}

	@Override
	public void delete(Long id) {
		Category category = getById(id);
		category.setActive(false);
		categoryRepository.save(category);
	}

	@Override
	public List<Category> getCategories() {		
		return categoryRepository.findByActiveTrue();
	}

}
