package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// 이유는 모르겠으나 config 패키지를 최상단에 두면 작동안됨, 아마 FrontController의 위치와 같은 위치여야하는 거같음

import com.cos.photogramstart.config.oauth.OAuth2DetailsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity // 해당 함수로 시큐리티를 활성화
@Configuration // Bean 등록
//요청 가로채기 : 각 요청에 대해서 보안 수준을 잘 조절하기 위한 키는 WebSecurityConfigurerAdapter의 configure(HttpSecurity) 메소드 오버라이딩 이다. 
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final OAuth2DetailsService oAuth2DetailsService;
	
	@Bean
	public BCryptPasswordEncoder encde() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//super.configure(http);
		/* 이거 때문에 기본 도메인 접속 시 요청을 가로채서 로그인 페이지로 리다이렉션 하는거 
		 *  super 삭제 - 기존 시큐리티가 가지고 있는 기능이 다 비활성화 됨
		 */
		
		http.csrf().disable(); // csrf 비활성화
		
		http.authorizeRequests()
			.antMatchers("/",  "/user/**", "/image/**", "/subcribe/**", "/comments/**", "api/**").authenticated() //다음 요청은 인증이 필요하고, 권한이 없으면 loginPage("/auth/signin")로 돌림
			.anyRequest().permitAll() // 그외 모든 요청은 허용하겠다.
			.and()
			.formLogin() // 로그인 페이지와 기타 로그인 처리 및 성공 실패 처리를 사용하겠다는 의미, formLogin은 form안의 input 태그들로 로그인하는것
			.loginPage("/auth/signin") // (get) 사용자가 따로 만든 로그인 페이지를 사용하려고 할때 설정
			.loginProcessingUrl("/auth/signin") // (post) 사용자 이름과 암호를 제출할 URL , 스프링 시큐리티가 이 주소로 요청이 들어오면 (UserDetailsService가) 낚아채서 로그인 프로세스 진행
			.defaultSuccessUrl("/") // 정상적으로 인증성공 했을 경우 이동하는 페이지를 설정
			.and()
			.oauth2Login()
			.userInfoEndpoint()
			.userService(oAuth2DetailsService);
			
	}
}

