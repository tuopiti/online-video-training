package com.piti.java.school.onlinevideotraining.service.impl;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.piti.java.school.onlinevideotraining.dto.VideoDTO;
import com.piti.java.school.onlinevideotraining.exception.ResourceNotFoundException;
import com.piti.java.school.onlinevideotraining.mapper.VideoMapper;
import com.piti.java.school.onlinevideotraining.model.Video;
import com.piti.java.school.onlinevideotraining.repository.VideoRepository;
import com.piti.java.school.onlinevideotraining.service.VideoService;
import com.piti.java.school.onlinevideotraining.utils.PageUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService{
	private final VideoRepository videoRepository;
	private final VideoMapper videoMapper;

	@Override
	public Video save(Video entity) {		
		return videoRepository.save(entity);
	}

	@Override
	public Video getById(Long id) {		
		return videoRepository.findById(id)
			    .orElseThrow(() -> new ResourceNotFoundException("Video", id));
	}

	@Override
	public Video update(Long id, VideoDTO dto) {		
		Video video = getById(id);
		videoMapper.update(video, dto);
		return videoRepository.save(video);
	}

	@Override
	public void delete(Long id) {
		Video video = getById(id);
		videoRepository.delete(video);		
	}

	@SuppressWarnings("serial")
	@Override
	public Page<Video> getVideos(Map<String, String> params) {
		Pageable pageable = PageUtils.getPageable(params);
		
		Specification<Video> specification = new Specification<Video>() {
			
			@Override
			public Predicate toPredicate(Root<Video> video, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (params.containsKey("title")) {
					String videoTitle = params.get("title");
					Predicate predicate = cb.like(video.get("title"),  "%" + videoTitle + "%");
					return predicate;
				}
				return null;
			}
			
		};
		
		Page<Video> page = videoRepository.findAll(specification, pageable);
		return page;
	}

}
