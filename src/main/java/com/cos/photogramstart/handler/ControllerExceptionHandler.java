package com.cos.photogramstart.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {
	 // CMRespDto, Script 비교
	 // 1. 클라이언트에게 응답 받을때는 Script 좋음 
	 // 2. 개발자가 응답 받을때는 CMRespDto 좋음
	 // 2. Ajax통신 - CMRespDto
	 // 3. Andorid 통신 - CMRespDto

	/* 방법 1
	 @ExceptionHandler(CustomValidationException.class) 
	 public CMRespDto<?> validationException(CustomValidationException e) { 
		 return new CMRespDto<Map<String, String>>(-1, e.getMessage(), e.getErrorMap());  //raw type 지양해야하니 <>명시해줘야한다.
	 }
	  */
	
	/*
	 // 방법 2
	 @ExceptionHandler(CustomValidationException.class) 
	 public String validationException(CustomValidationException e) {
		 return Script.back(e.getErrorMap().toString());
	 }
	 */
	
	 @ExceptionHandler(CustomException.class) 
	 public String customException(CustomException e) {
		 return Script.back(e.getMessage());
	 }
	 
	 @ExceptionHandler(CustomApiException.class) 
	 public ResponseEntity<?> apiException(CustomApiException e) {
		 return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), null), HttpStatus.BAD_REQUEST);
	 }
	
	 @ExceptionHandler(CustomValidationException.class) 
	 public String validationException(CustomValidationException e) {
		 if(e.getErrorMap() == null) {
			 return Script.back(e.getMessage());
		 }else {
			 return Script.back(e.getErrorMap().toString());
		 }
	 }
	 
	 @ExceptionHandler(CustomValidationApiException.class) 
	 public ResponseEntity<?> validationApiException(CustomValidationApiException e) {
		 return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);
	 }
	 
}
