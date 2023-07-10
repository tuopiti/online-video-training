package com.piti.java.school.onlinevideotraining.spec;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.piti.java.school.onlinevideotraining.model.Category;
import com.piti.java.school.onlinevideotraining.model.Category_;
import com.piti.java.school.onlinevideotraining.model.Course;
import com.piti.java.school.onlinevideotraining.model.Course_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@SuppressWarnings("serial")
@RequiredArgsConstructor
public class CourseSpec implements Specification<Course>{
	private final CourseFilter courseFilter;

	@Override
	public Predicate toPredicate(Root<Course> course, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> list = new ArrayList<>();
		
		Join<Course, Category> category = course.join("category");
		
		if(courseFilter.getCourseId() != null) {
			Predicate courseId = course.get(Course_.ID).in(courseFilter.getCourseId());
			list.add(courseId);
		}
		
		if(courseFilter.getCourseName() != null) {
			Predicate courseName = cb.like(course.get(Course_.NAME), "%" + courseFilter.getCourseName() + "%");
			list.add(courseName);
		}
		
		if(courseFilter.getCategoryId() != null) {
			Predicate categoryId = category.get(Category_.ID).in(courseFilter.getCategoryId());
			list.add(categoryId);
		}
		
		if(courseFilter.getCategoryName() != null) {
			Predicate categoryName = cb.like(category.get(Category_.NAME),  "%" + courseFilter.getCategoryName() + "%");
			list.add(categoryName);
		}
		Predicate[] predicates = list.toArray( Predicate[]::new);
		return cb.and(predicates);		
	}

}
