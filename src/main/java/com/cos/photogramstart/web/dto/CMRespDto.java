package com.cos.photogramstart.web.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CMRespDto<T> {
	// Exception 발생 시 e.getMessage(), e.getErrorMap() 두 값을 return 하기 위해 만들어준 클래스 
	
	private int code; //1(성공), -1(실패)
	private String message;
	private T data;
}
