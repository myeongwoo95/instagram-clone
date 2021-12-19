package com.cos.photogramstart.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/user/{id}")
	public String profile(@PathVariable int id, Model model) {
		User userEntity = userService.회원프로필(id);
		model.addAttribute("user", userEntity);
		return "/user/profile";
	}
	
	@GetMapping("/user/{id}/update")
	public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		//세션 > SecurityContextHolder > Authentication객체 > PrincipalDetails객체 > User객체
		
		//System.out.println("세션 정보: " + principalDetails.getUser()); // 이 방법 사용
		
		// Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
		// System.out.println("직접 찾은 세션 정보:" + ((PrincipalDetails)auth.getPrincipal()).getUser()); // getPrincipal() 반환값은 오브젝트라 캐스팅 필요

		return "/user/update";
	}

}
