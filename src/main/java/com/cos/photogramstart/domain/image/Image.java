package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cos.photogramstart.domain.subcribe.Subscribe;
import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor 
@NoArgsConstructor
@Data
@Entity // @Entity가 붙은 클래스는 JPA가 관리하는 클래스로, 해당 클래스를 엔티티라고 한다. @Entity는 DB에 테이블을 자동으로 생성해준다.
public class Image { // N, 1 = N : 1
	
	@Id // primary key 설정, 없으면 에러
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다.(mysql = autoincreasement, oracle= sequence)
	private int id;
	private String caption;
	private String postImageUrl;
	
	@JoinColumn(name = "userId")
	@ManyToOne
	private User user; // 1, 1 = N : 1
	
	// 이미지 좋아요
	// 댓글
	
	private LocalDateTime createDate;
	
	@PrePersist // @PrePersist는 DB에 Insert 되기 직전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
	
	// 오브젝트를 콘솔에 출력할 때 문제가 될 수 있어서 user 부분을 출력되지 않게 함
	@Override
	public String toString() {
		return "Image [id=" + id + ", caption=" + caption + ", postImageUrl=" + postImageUrl
				+ ", createDate=" + createDate + "]";
	}
	
}
