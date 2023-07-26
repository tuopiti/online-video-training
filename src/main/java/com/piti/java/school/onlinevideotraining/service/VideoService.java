package com.piti.java.school.onlinevideotraining.service;

import java.util.Map;

import org.springframework.data.domain.Page;

import com.piti.java.school.onlinevideotraining.dto.VideoDTO;
import com.piti.java.school.onlinevideotraining.model.Video;

public interface VideoService {
	Video save(Video entity);
	
	Video getById(Long id);
	
	Video update(Long id, VideoDTO dto);
	
	void delete(Long id);
	
	Page<Video> getVideos(Map<String, String> params);
}
