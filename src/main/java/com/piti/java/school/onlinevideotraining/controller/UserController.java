package com.piti.java.school.onlinevideotraining.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.piti.java.school.onlinevideotraining.model.User;
import com.piti.java.school.onlinevideotraining.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/registration")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	
	@PostMapping
    public String register(@RequestBody User user) {
        return userService.registerUser(user);
    }
	
	@GetMapping(path = "confirm")
	public String confirm(@RequestParam("token") String token) {		
		return userService.confirmTokenEmail(token);
		
	}
}
