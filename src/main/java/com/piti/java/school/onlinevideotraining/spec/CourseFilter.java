package com.piti.java.school.onlinevideotraining.spec;

import lombok.Data;

@Data
public class CourseFilter {
	private Long courseId;
	private String courseName;
	private String categoryName;
	private Long categoryId;
}
