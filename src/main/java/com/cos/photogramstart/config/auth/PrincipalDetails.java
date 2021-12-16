package com.cos.photogramstart.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	private User user;
	
	// User 객체를 받는 생성자
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	//권한은 한개가 아닐 수 있기때문에 컬렉션으로 리턴한다.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collector = new ArrayList<>();
		collector.add(()-> {return user.getRole();});
		// add안에 함수를 넣고싶은건데 자바의 매개변수에 함수를 못넣는다. 왜냐하면 자바의 함수는 1급객체가 아니기 때문이다.
		// 1급객체의 조건 3가지 : 1.변수에 할당가능 2.객체의 인자로 넘길 수 있어야함 3.객체의 리턴값으로 리턴 될 수 있어야함
		// 그래서 함수를 들고있는 인터페이스를 넣어야한다. 
		// 자바에서 함수를 파라미터로 전달하고싶으면 자바에서는 무저건 인터페이스를 넘기게 되어있다 (오브젝트도 가능)
		// 즉 람다식으로 함수를 파라미터로 넘겨주면 된다 (그럼 람다식은 인터페이스인가...?)

		return collector;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	//이 밑에 것들은 실제 서비스할때 필요한 것들이다.
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
