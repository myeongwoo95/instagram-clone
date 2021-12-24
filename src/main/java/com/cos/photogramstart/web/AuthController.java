package com.cos.photogramstart.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.Auth.SignupDto;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor // 초기화 되지 않은 모든 final 필드, @NonNull로 마크돼있는 모든 필드들에 대한 생성자를 자동으로 생성해줍니다.
@Controller
public class AuthController {
	
	private final AuthService authService; //전역변수에 final이 걸려있으면 객체가 생성될때 무저건 초기화해야한다. 
	private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	@GetMapping("/auth/signin")
	public String signinForm() {
		return "auth/signin";
	}
	
	@GetMapping("/auth/signup")
	public String signupForm() {
		return "auth/signup";
	}
	
	@PostMapping("/auth/signup") //CSRF 토큰 해제 설정 or 토큰도 함께 보내야함
	//첫번째 파라미터에서 에러가 발생하면, 오른쪽 파라미터로 담아준다.
	public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) {
		
			//User < - signupDto
			User user = signupDto.toEntity();
			authService.회원가입(user); // service로 객체 데이터는 항상 Dto가 아니라 Entity!!
			//System.out.println(userEntity);
			return "auth/signin";
	}
	
}
