package com.piti.java.school.onlinevideotraining.exception;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class ResourceNotFoundException extends ApiException{
	
	public ResourceNotFoundException(String resourceName, Long id) {
		super(HttpStatus.NOT_FOUND, String.format("%s With id = %d not found",resourceName,id));
	}

}
