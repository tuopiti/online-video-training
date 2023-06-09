package com.piti.java.school.onlinevideotraining.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.piti.java.school.onlinevideotraining.dto.CategoryDTO;
import com.piti.java.school.onlinevideotraining.model.Category;

@Mapper
public interface CategoryMapper {	
	CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
	
	Category toCategory(CategoryDTO dto);
	
	CategoryDTO toCategoryDTO(Category entity);
}
