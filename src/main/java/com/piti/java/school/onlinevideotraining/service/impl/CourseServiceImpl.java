package com.piti.java.school.onlinevideotraining.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.piti.java.school.onlinevideotraining.dto.CourseDTO;
import com.piti.java.school.onlinevideotraining.exception.ResourceNotFoundException;
import com.piti.java.school.onlinevideotraining.mapper.CourseMapper;
import com.piti.java.school.onlinevideotraining.model.Course;
import com.piti.java.school.onlinevideotraining.repository.CourseRepository;
import com.piti.java.school.onlinevideotraining.service.CourseService;
import com.piti.java.school.onlinevideotraining.spec.CourseFilter;
import com.piti.java.school.onlinevideotraining.spec.CourseSpec;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService{
	private final CourseRepository courseRepository;	
	private final CourseMapper courseMapper;
    
	@Override
	public Course save(Course entity) {		
		return courseRepository.save(entity);
	}

	@Override
	public Course getById(Long id) {		
		return courseRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Course", id));
	}

	@Override
	public Course update(Long id, CourseDTO dto) {
		/*
		Course course = getById(id);
		course.setName(dto.getName());
		course.setCategory(categoryService.getById(dto.getCategoryId().longValue()));
		return courseRepository.save(course);
		*/
		Course course = getById(id);
		courseMapper.update(course, dto);
		return courseRepository.save(course);
	}

	@Override
	public void delete(Long id) {
		Course course = getById(id);
		courseRepository.delete(course);
		
	}

	@Override
	public List<Course> getCourses(Map<String, String> params) {
		CourseFilter courseFilter = new CourseFilter();
		if(params.containsKey("courseId")) {
			courseFilter.setCourseId(MapUtils.getLong(params,"courseId"));
		}
		
		if(params.containsKey("courseName")) {
			courseFilter.setCourseName(MapUtils.getString(params,"courseName"));
		}
		
		if(params.containsKey("categoryId")) {
			courseFilter.setCategoryId(MapUtils.getLong(params,"categoryId"));
		}
		
		if(params.containsKey("categoryName")) {
			courseFilter.setCategoryName(MapUtils.getString(params,"categoryName"));
		}
		
		CourseSpec courseSpec = new CourseSpec(courseFilter);
		return courseRepository.findAll(courseSpec, Sort.by(Order.asc("id")));
	}

	
}
