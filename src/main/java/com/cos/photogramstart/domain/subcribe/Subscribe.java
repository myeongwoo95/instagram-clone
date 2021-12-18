package com.cos.photogramstart.domain.subcribe;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor 
@NoArgsConstructor
@Data
@Table( //복합키를 유니크로 설정할때
		uniqueConstraints = {
				@UniqueConstraint(
						name="subscribe_uk",
						columnNames= {"fromUserId", "toUserId"}
				)
		}
)
@Entity // @Entity가 붙은 클래스는 JPA가 관리하는 클래스로, 해당 클래스를 엔티티라고 한다. @Entity는 DB에 테이블을 자동으로 생성해준다.
public class Subscribe {
	
	@Id // primary key 설정, 없으면 에러
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다.(mysql = autoincreasement, oracle= sequence)
	private int id; 
	
	@JoinColumn(name = "fromUserId") // 니 맘대로 만들지 말고!!
	@ManyToOne
	private User fromUser; // 구독자 하는 친구
	
	@JoinColumn(name = "toUserId")
	@ManyToOne
	private User toUser; //  구독 받는애
	
	private LocalDateTime createDate;
	
	@PrePersist // @PrePersist는 DB에 Insert 되기 직전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

}
