package com.cos.photogramstart.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service // IoC
public class PrincipalDetailsService implements UserDetailsService{ //낚아채는 놈은 UserDetailsService
	
	private final UserRepository userRepository;
	
	// 1) 비밀번호는 처리하지 않아도 된다. 스프링 시큐리티에서 알아서 해준다
	// 2) 우리가 할 일은 아이디의 존재 여부이다.
	//  	a. 아이디가 존재하지않으면 null 반환
	//	    b. 아이디가 존재하면 UserDetails타입반환 (UserDetails를 implements 한 PrincipalDetails에 UserEntity를 넣고 반환)
	// 3) UserDetails를 implements 한 PrincipalDetails 클래스 작성
	// 4) 아이디 존재 여부를 찾기위해 UserRepository에서 query Methods(메소드 이름으로 쿼리 생성)로 username을 찾아주는 쿼리(메서드)를 만든다.
	// 5) 로그인 기능을 구현하기 위해 실제 session에 담기는 것은 PrincipalDetails이고 PrincipalDetails안에 UserEntity가 있으니 그것을 활용하는 것임

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User userEntity  = userRepository.findByUsername(username);
		
		if(userEntity == null) {
			return null;
		}else {
			return new PrincipalDetails(userEntity); // PrincipalDetails가 세션에 저장되는데 PrincipalDetails안에 User 정보가 있다.
		}
	}
	
}
