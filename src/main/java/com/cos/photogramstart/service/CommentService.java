package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.comment.CommentRespository;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRespository commentRespository;
	private final UserRepository userRepository;
	
	@Transactional
	public Comment 댓글쓰기(String content, int imageId, int userId) {
		
		// Tip (객체를 만들 때 id 값만 담아서 insert 할 수 있다)
		// 대신 return 시에 image, user 객체는 id 값만 가지고 있는 빈 객체를 리턴 받는다.
		Image image = new Image();
		image.setId(imageId);
		
		User userEntity = userRepository.findById(userId).orElseThrow(()->{
			throw new CustomApiException("유저 아이디를 찾을 수 없습니다.");
		});
		
		Comment comment = new Comment();
		
		comment.setContent(content);
		comment.setImage(image);
		comment.setUser(userEntity);
		
		return commentRespository.save(comment);
	}
	
	@Transactional
	public void 댓글삭제() {
		
	}
}
