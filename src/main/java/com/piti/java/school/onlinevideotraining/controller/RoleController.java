package com.piti.java.school.onlinevideotraining.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piti.java.school.onlinevideotraining.service.RoleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/role")
@RequiredArgsConstructor
public class RoleController {
	private final RoleService roleService;
	
	@PostMapping("/assign/{userId}/{roleId}")
	public ResponseEntity<?> assignRole(@PathVariable Long userId, 
	                         @PathVariable Long roleId){
	    roleService.assignUserRole(userId, roleId);
	    return ResponseEntity.ok().build();
	}
	
	@PostMapping("/unassign/{userId}/{roleId}")
	public ResponseEntity<?> unassignRole(@PathVariable Long userId,
	                           @PathVariable Long roleId){
	    roleService.unassignUserRole(userId, roleId);
	    return ResponseEntity.ok().build();
	}
}
