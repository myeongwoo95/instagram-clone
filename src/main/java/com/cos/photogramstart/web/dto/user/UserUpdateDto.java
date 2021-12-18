package com.cos.photogramstart.web.dto.user;

import javax.validation.constraints.NotBlank;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class UserUpdateDto {
	@NotBlank
	private String name; //필수
	
	@NotBlank
	private String password; //필수
	private String website;
	private String bio;
	private String phone;
	private String gender;
	
	// 조금 위험함. 코드 수정이 필요할 예정
	public User toEntity() {
		return User.builder()
				.name(name) // validation 체크
				.password(password) // 패스워드를 기재하지 안했으면 업데이트할때 패스워드가 사라져서 문제! validation 체크!
				.website(website)
				.bio(bio)
				.phone(phone)
				.gender(gender)
				.build();
	}
}
