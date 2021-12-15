package com.cos.photogramstart.handler.ex;

import java.util.Map;

import lombok.Getter;

public class CustomValidationException extends RuntimeException{
	
	//시리얼 번호는 객체를 구분할때 사용, 이것은 우리한테 중요한게아니라 JVM에게 중요한거
	private static final long serialVersionUID =1L;
	
	private Map<String, String> errorMap;
	
	public CustomValidationException(String message, Map<String, String> errorMap) {
		/*
		 * 부모객체에 message 변수가 있고 super로 넘겨 준 후 값 설정을 한다. 또한 부모객체에는 getMessage()로 meesage를
		 * 받아올 수 있다, 그래서 errorMap getter만 만들어주면 된다.
		 */
		super(message); 
		this.errorMap = errorMap;
	}
	
	public Map<String, String> getErrorMap(){
		return errorMap;
	}
}
