package com.cos.photogramstart.handler.ex;

import java.util.Map;

import lombok.Getter;

public class CustomApiException extends RuntimeException{
	
	//시리얼 번호는 객체를 구분할때 사용, 이것은 우리한테 중요한게아니라 JVM에게 중요한거
	private static final long serialVersionUID =1L;

	public CustomApiException(String message) {
		super(message); 
	}
}
