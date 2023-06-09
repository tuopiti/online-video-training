package com.piti.java.school.onlinevideotraining.service;

import java.util.List;

import com.piti.java.school.onlinevideotraining.model.Category;

public interface CategoryService {
	Category save(Category entity);

	Category getById(Long id);

	Category update(Long id, Category categoryUpdate);
	
	void delete(Long id);
	
	List<Category> getCategories();
}
