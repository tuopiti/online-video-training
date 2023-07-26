package com.piti.java.school.onlinevideotraining.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.piti.java.school.onlinevideotraining.config.security.AuthUser;
import com.piti.java.school.onlinevideotraining.model.User;

@Mapper
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	
	AuthUser toAuthUser(User user);
}
