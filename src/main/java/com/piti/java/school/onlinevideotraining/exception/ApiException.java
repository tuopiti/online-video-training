package com.piti.java.school.onlinevideotraining.exception;

import org.springframework.http.HttpStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper=false)
@Data
@RequiredArgsConstructor
public class ApiException extends RuntimeException{
	private final HttpStatus status;
	private final String message;
	
}
