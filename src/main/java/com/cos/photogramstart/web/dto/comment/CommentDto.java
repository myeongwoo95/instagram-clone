package com.cos.photogramstart.web.dto.comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CommentDto {
	// NotNull : null 체크
	// NotEmpty : 빈값, null 체크
	// NotBlank : 빈값이거나 null 체크 그리고 빈 공백(스페이스)
	
	@NotBlank 
	private String content;
	@NotNull 
	private Integer imageId;
	
	// toEntity가 필요없다.
}
