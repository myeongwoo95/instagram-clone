package com.cos.photogramstart.handler.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;

@Component // RestController, Service 모든 것들이 Component를 상속해서 만들어졌음 
@Aspect
public class ValidationAdvice {

	// * com.cos... 는 public, protected, private 등등 함수의 접근제어를 지정, *은 All
	// *Controller.*(..) 여기서 (..) 메서드의 파라미터가 뭐든 상관없는것, 
	// proceddingJoinPoint는 핵심로직 메서드의 매겨변수 뿐만 아니라 메서드의 내부까지도 접근할 수 있는 파라미터이다.
	@Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))") 
	public Object apiAdvice(ProceedingJoinPoint proceddingJoinPoint) throws Throwable { 
		System.out.println("web api 컨트롤러 시작 전! ======================");
		
		Object[] args = proceddingJoinPoint.getArgs();
		for (Object arg: args) { // 핵심로직 메서드의 모든 매개변수를 뽑아서 간단하게 출력
			//System.out.println(arg); // toString인해 무한참조가 발생할 수 있음, JsonIgnore랑은 별개임
			
			if(arg instanceof BindingResult) {
				BindingResult bindingResult = (BindingResult) arg;
				if(bindingResult.hasErrors()) {
					Map<String, String> errorMap = new HashMap<>();
					
					for(FieldError error: bindingResult.getFieldErrors()) { 
						errorMap.put(error.getField(), error.getDefaultMessage());
						System.out.println(error.getDefaultMessage());
					}
					
					throw new CustomValidationApiException("유효성 검사 실패함", errorMap);
				}
			}
		}
		
		return proceddingJoinPoint.proceed(); // 이때 핵심로직 메서드가 실행됨 
	}
	
	@Around("execution(* com.cos.photogramstart.web.*Controller.*(..))") 
	public Object advice(ProceedingJoinPoint proceddingJoinPoint) throws Throwable {
		System.out.println("web 컨트롤러 시작 전! ========================");
		
		Object[] args = proceddingJoinPoint.getArgs();
		for (Object arg: args) { 

			if(arg instanceof BindingResult) {
				BindingResult bindingResult = (BindingResult) arg;
				if(bindingResult.hasErrors()) {
					Map<String, String> errorMap = new HashMap<>();
					
					for(FieldError error: bindingResult.getFieldErrors()) {
						errorMap.put(error.getField(), error.getDefaultMessage());
						System.out.println(error.getDefaultMessage());
					}
					
					throw new CustomValidationException("유효성 검사 실패함", errorMap);
				}
			}
		}
		
		return proceddingJoinPoint.proceed();
	}
}
