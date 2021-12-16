package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor //모든 값을 파라미터로 받는 생성자 생성
@NoArgsConstructor //빈 생성자 생성
@Data
@Entity // @Entity가 붙은 클래스는 JPA가 관리하는 클래스로, 해당 클래스를 엔티티라고 한다. @Entity는 DB에 테이블을 자동으로 생성해준다.
public class User {
	
	@Id // primary key 설정, 없으면 에러
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다.(mysql = autoincreasement, oracle= sequence)
	private int id; // Long으로 해야하는데 토이프로젝트이니까
	
	@Column(length = 20, unique = true)
	private String username;
	
	@Column(nullable=false)
	private String password;
	
	@Column(nullable=false)
	private String name;
	private String website;
	private String bio; //자기소개
	
	@Column(nullable=false)
	private String email;
	
	private String phone;
	private String gender;
	
	private String profileImageUrl;
	private String role;
	
	
	private LocalDateTime createDate;
	
	@PrePersist // @PrePersist는 DB에 Insert 되기 직전에 실행
	//스프링 부트와 AWS로 혼자 구현하는 웹서비스 책에서 createDate와 updateDate 값을 가진 클래스를 만들어서 상속하는 방식으로 좀더 객체지향적으로 사용하는 방법이 있다. 
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
	
}
