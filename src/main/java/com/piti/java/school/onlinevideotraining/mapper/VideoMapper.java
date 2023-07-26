package com.piti.java.school.onlinevideotraining.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.piti.java.school.onlinevideotraining.dto.VideoDTO;
import com.piti.java.school.onlinevideotraining.model.Video;
import com.piti.java.school.onlinevideotraining.service.CourseService;

@Mapper(componentModel = "spring", uses = {CourseService.class})
public interface VideoMapper {
	VideoMapper INSTANCE = Mappers.getMapper(VideoMapper.class);
	
	@Mapping(target = "course", source = "courseId")
	Video toVideo(VideoDTO dto);
	
	@Mapping(target = "courseId", source = "course.id")
	VideoDTO toDTO(Video video);
	
	@Mapping(target = "course", source = "courseId")
	@Mapping(target = "id", ignore = true)
	void update(@MappingTarget Video video, VideoDTO dto);
}
