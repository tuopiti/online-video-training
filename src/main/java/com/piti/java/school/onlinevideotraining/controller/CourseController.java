package com.piti.java.school.onlinevideotraining.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.piti.java.school.onlinevideotraining.dto.CourseDTO;
import com.piti.java.school.onlinevideotraining.mapper.CourseMapper;
import com.piti.java.school.onlinevideotraining.model.Course;
import com.piti.java.school.onlinevideotraining.service.CourseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController {
	private final CourseService courseService;
	private final CourseMapper courseMapper;
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody CourseDTO dto){		
		Course course = courseMapper.toCourse(dto);
		courseService.save(course);
		CourseDTO CourseDTO= CourseMapper.INSTANCE.toDTO(course);
		return ResponseEntity.ok(CourseDTO);
		
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable("id") Long id){
		Course course = courseService.getById(id);
		return ResponseEntity.ok(CourseMapper.INSTANCE.toDTO(course));
		
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody CourseDTO dto){
		Course course = courseService.update(id, dto);		
		return ResponseEntity.ok(CourseMapper.INSTANCE.toDTO(course));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Long id){
		courseService.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public ResponseEntity<?> getCourseList(@RequestParam Map<String, String> params){
		List<CourseDTO> list = courseService.getCourses(params)
					.stream()
					.map(c -> CourseMapper.INSTANCE.toDTO(c))
					.toList();
		return ResponseEntity.ok(list);
		
	}
		
	
}
