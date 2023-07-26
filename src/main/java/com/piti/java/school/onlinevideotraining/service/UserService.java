package com.piti.java.school.onlinevideotraining.service;

import java.util.Optional;

import com.piti.java.school.onlinevideotraining.config.security.AuthUser;
import com.piti.java.school.onlinevideotraining.model.User;

public interface UserService {
	Optional<AuthUser> loadUserByEmail(String email);
	
	String registerUser(User user);
	
	String confirmTokenEmail(String token);
}
