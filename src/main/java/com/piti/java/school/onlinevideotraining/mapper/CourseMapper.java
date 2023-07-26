package com.piti.java.school.onlinevideotraining.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.piti.java.school.onlinevideotraining.dto.CourseDTO;
import com.piti.java.school.onlinevideotraining.model.Course;
import com.piti.java.school.onlinevideotraining.service.CategoryService;

@Mapper(componentModel = "spring", uses = {CategoryService.class})
public interface CourseMapper {
	CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);
	
	@Mapping(target = "category", source = "categoryId")
	Course toCourse(CourseDTO dto);
	
	@Mapping(target = "categoryId", source = "category.id")
	CourseDTO toDTO(Course course);	
	
	@Mapping(target = "category", source = "categoryId")
	@Mapping(target = "id", ignore = true)
	void update(@MappingTarget Course course, CourseDTO dto);
}
