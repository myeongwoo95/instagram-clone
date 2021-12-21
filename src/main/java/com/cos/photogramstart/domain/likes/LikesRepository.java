package com.cos.photogramstart.domain.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikesRepository extends JpaRepository<Likes, Integer>{

	// 이게 가끔 JPA를 안쓰는게 더 편한 이유다. Likes를 save하기 위해서는 이미지 객체와 유저 객체를 셀렉트해서 넣어줘야 한다...
	@Modifying
	@Query(value="insert into likes(imageId, userId, createDate) values(:imageId, :principalId, now())", nativeQuery = true)
	int mLikes(int imageId, int principalId);
	
	@Modifying
	@Query(value="delete from likes where imageId = :imageId and userId = :principalId", nativeQuery = true)
	int mUnLikes(int imageId, int principalId);
}
