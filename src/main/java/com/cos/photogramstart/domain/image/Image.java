package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.likes.Likes;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
	
	@JsonIgnoreProperties({"images"})
	@JoinColumn(name = "userId")
	@ManyToOne
	private User user; // 1, 1 = N : 1
	
	// 무한참조됨
	@JsonIgnoreProperties({"image"})
	@OneToMany(mappedBy = "image") // 나는 연관관계의 주인이 아니에요 FK 만들지 마세요!!!
	private List<Likes> likes;
	
	//DB에 컬럼이 만들어지지 않는다.
	@Transient // javax.Persistence
	private boolean likeState;
	
	@Transient // javax.Persistence
	private int likeCount;
	
	@OrderBy("id DESC") // javax.persistence
	@JsonIgnoreProperties({"image"})
	@OneToMany(mappedBy = "image")
	private List<Comment> comments;
	
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
