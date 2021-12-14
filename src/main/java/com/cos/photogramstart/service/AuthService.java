package com.cos.photogramstart.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service // 1.IoC  2.트랜잭션 처리
public class AuthService {
		
	private final UserRepository userReposeRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional // Insert, Update, Delete 사용할 때 넣어줌
	public User 회원가입(User user) {
		String rawPassword = user.getPassword();
		String encPassowrd = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassowrd);
		user.setRole("ROLE_USER");
		User userEntity = userReposeRepository.save(user); // return type : S save(S entity);
		return userEntity;
		
	}
}
