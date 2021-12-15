package com.cos.photogramstart.handler;

import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {

	/*
	 //CustomValidationException가 발생한다면 (RuntimeException이 상속되었으니 RuntimeException이 발생한다면으로 해석)
	 @ExceptionHandler(CustomValidationException.class) 
	 public CMRespDto<?> validationException(CustomValidationException e) { 
		 return new CMRespDto<Map<String, String>>(-1, e.getMessage(), e.getErrorMap()); 
		 //raw type 지양해야하니 <>명시해줘야한다.
	 }
	*/
	
	 // 2번째 방법
	 @ExceptionHandler(CustomValidationException.class) 
	 public String validationException(CustomValidationException e) {
		 return Script.back(e.getErrorMap().toString());
		 // CMRespDto, Script 비교
		 // 1. 클라이언트에게 응답 받을때는 Script 좋음 
		 // 2. 개발자가 응답 받을때는 CMRespDto 좋음
		 // 2. Ajax통신 - CMRespDto
		 // 3. Andorid 통신 - CMRespDto
	 }
	 
}
