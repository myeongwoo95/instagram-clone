package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.service.SubscribeService;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserApiController {
	
	private final UserService userService;
	private final SubscribeService subscribeService;
	
	@PutMapping("/api/user/{principalId}/profileImageUrl")
	public ResponseEntity<?> profileImageUrlUpdate(@PathVariable int principalId, MultipartFile profileImageFile, @AuthenticationPrincipal PrincipalDetails principalDetails){ 
		User userEntity = userService.회원프로필사진변경(principalId, profileImageFile);
		
		principalDetails.setUser(userEntity); // 회원 사진이 변경되고 나면 session 값이 바뀌어야 한다!
		return new ResponseEntity<>(new CMRespDto<>(1, "프로필사진변경 성공", null), HttpStatus.OK);
	}
	
	@GetMapping("/api/user/{pageUserId}/subscribe")
	public ResponseEntity<?> subscribeList(@PathVariable int pageUserId, @AuthenticationPrincipal PrincipalDetails principalDetails){
		
		List<SubscribeDto> subscribeDto = subscribeService.구독리스트(principalDetails.getUser().getId(), pageUserId);
		
		return new ResponseEntity<>(new CMRespDto<>(1, "팔로우 리스트 가져오기 성공", subscribeDto), HttpStatus.OK);
	}
	
	@GetMapping("/api/user/{pageUserId}/followers")
	public ResponseEntity<?> followerList(@PathVariable int pageUserId, @AuthenticationPrincipal PrincipalDetails principalDetails){
		
		List<SubscribeDto> subscribeDto = subscribeService.팔로워리스트(principalDetails.getUser().getId(), pageUserId);
		
		return new ResponseEntity<>(new CMRespDto<>(1, "팔로워 리스트 가져오기 성공", subscribeDto), HttpStatus.OK);
	}
	
	
	
	@PutMapping("/api/user/{id}")
	public CMRespDto<?> update(
			@PathVariable int id,
			@Valid UserUpdateDto userUpdateDto,
			BindingResult bindingResult, // 꼭 @Valid가 적혀있는 다음 파라미터에 적어야함
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
			User userEntity = userService.회원수정(id, userUpdateDto.toEntity());
			principalDetails.setUser(userEntity); // ★update 이후에 session 정보를 초기화 해주어야한다. 그래야 바뀐 값이 화면에 적용된다!
			return new CMRespDto<>(1, "회원수정완료", userEntity); // 응답시에 userEntity의 모든 getter 함수가 호출되고 JSON으로 파싱하여 응답한다.(MessageConverter 발동)
	}
}
