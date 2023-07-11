package com.piti.java.school.onlinevideotraining.dto;

import lombok.Data;

@Data
public class VideoDTO {
	private Long id;
    private String title;
    private String description;
    private String link;
    private String imageCover;
    private Integer courseId;
}
