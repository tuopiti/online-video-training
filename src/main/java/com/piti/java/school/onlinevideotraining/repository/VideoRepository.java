package com.piti.java.school.onlinevideotraining.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.piti.java.school.onlinevideotraining.model.Video;

public interface VideoRepository extends JpaRepository<Video, Long>, JpaSpecificationExecutor<Video>{

}
