package com.cos.blog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.web.dto.CMRespDto;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(value = IllegalArgumentException.class)
	public CMRespDto<?> illegalArgument(Exception e){
		
		return new CMRespDto<>(-1, null);
	}

}
