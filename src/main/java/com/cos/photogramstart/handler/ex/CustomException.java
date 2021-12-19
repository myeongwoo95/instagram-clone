package com.cos.photogramstart.handler.ex;

import java.util.Map;

import lombok.Getter;

public class CustomException extends RuntimeException{
	
	private static final long serialVersionUID =1L;
	
	public CustomException(String message) {
		super(message); 
	}
}
