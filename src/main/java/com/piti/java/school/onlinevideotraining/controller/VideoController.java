package com.piti.java.school.onlinevideotraining.controller;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.piti.java.school.onlinevideotraining.dto.VideoDTO;
import com.piti.java.school.onlinevideotraining.dto.page.PageDTO;
import com.piti.java.school.onlinevideotraining.mapper.PageMapper;
import com.piti.java.school.onlinevideotraining.mapper.VideoMapper;
import com.piti.java.school.onlinevideotraining.model.Video;
import com.piti.java.school.onlinevideotraining.service.VideoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/videos")
@RequiredArgsConstructor
public class VideoController {
	private final VideoService videoService;
	private final VideoMapper videoMapper;
	
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_AUTHOR')")
	@PostMapping
	public ResponseEntity<?> create(@RequestBody VideoDTO dto){
		Video video = videoMapper.toVideo(dto);
		videoService.save(video);		
		return ResponseEntity.ok(VideoMapper.INSTANCE.toDTO(video));		
	}
	
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_AUTHOR')")
	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable("id") Long id){
		Video video = videoService.getById(id);
		return ResponseEntity.ok(VideoMapper.INSTANCE.toDTO(video));
	}
	
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_AUTHOR')")
	@PutMapping("{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody VideoDTO dto){
		Video video = videoService.update(id, dto);		
		return ResponseEntity.ok(VideoMapper.INSTANCE.toDTO(video));
	}
	
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_AUTHOR')")
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Long id){
		videoService.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public ResponseEntity<?> getVideoList(@RequestParam Map<String, String> params){
		Page<Video> page = videoService.getVideos(params);
		PageDTO dto = PageMapper.INSTANCE.toDTO(page);
		dto.setList(page.get().map(VideoMapper.INSTANCE::toDTO).toList());
		return ResponseEntity.ok(dto);	
	}
}
