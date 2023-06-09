package com.piti.java.school.onlinevideotraining.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piti.java.school.onlinevideotraining.dto.CategoryDTO;
import com.piti.java.school.onlinevideotraining.mapper.CategoryMapper;
import com.piti.java.school.onlinevideotraining.model.Category;
import com.piti.java.school.onlinevideotraining.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
	
	private final CategoryService categoryService;

	@PostMapping
	public ResponseEntity<?> create(@RequestBody CategoryDTO categoryDTO){
		Category category = CategoryMapper.INSTANCE.toCategory(categoryDTO);
		category = categoryService.save(category);
		return ResponseEntity.ok(category);
		
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable("id") Long id){
		return ResponseEntity.ok(categoryService.getById(id));
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody CategoryDTO categoryDTO){
		Category category = CategoryMapper.INSTANCE.toCategory(categoryDTO);
		Category updateCategory = categoryService.update(id, category);
		return ResponseEntity.ok(CategoryMapper.INSTANCE.toCategoryDTO(updateCategory));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Long id){
		categoryService.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public ResponseEntity<?> list(){		
		List<CategoryDTO> listCategory = categoryService.getCategories().stream()
				.map(b -> CategoryMapper.INSTANCE.toCategoryDTO(b))
				.toList();	
		return ResponseEntity.ok(listCategory);
	}
}
