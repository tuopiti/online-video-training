package com.piti.java.school.onlinevideotraining.service;

import java.util.List;
import java.util.Map;

import com.piti.java.school.onlinevideotraining.dto.CourseDTO;
import com.piti.java.school.onlinevideotraining.model.Course;



public interface CourseService {
	Course save(Course entity);
	
	Course getById(Long id);
	
	Course update(Long id, CourseDTO dto);
	
	void delete(Long id);
	
	List<Course> getCourses(Map<String, String> params);	
}
