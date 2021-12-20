package com.cos.photogramstart.domain.subcribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer>{

		@Modifying // insert, delete, update를 네이티브 쿼리로 작성하려면 해당 어노테이션이 필요!!
		@Query( value = "INSERT INTO subscribe(fromUserId, toUserId, createDate) VALUES(:fromUserId, :toUserId, now())", nativeQuery = true)
		// 성공하면 변경된 행의 개수가 리턴, 실패하면 -1, 리턴된 값으로 핸들링 하는건 옛날 방식
		// 반환된 값으로 뭘 하기보다 그냥 예외처리하는게 나음(-1는 터진거니까)
		void mSubscribe(int fromUserId, int toUserId); 
		
		@Modifying
		@Query(value = "DELETE FROM subscribe WHERE fromUserId = :fromUserId AND toUserId = :toUserId", nativeQuery = true)
		void mUnSubscribe(int fromUserId, int toUserId);
		
		
		@Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId = :principalId AND toUserId = :pageUserId", nativeQuery = true)
		int mSubscribeState(int principalId, int pageUserId);
		
		@Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId = :pageUserId", nativeQuery = true)
		int mSubscribeCount(int pageUserId);
		
		@Query(value = "SELECT COUNT(*) FROM subscribe WHERE toUserId = :pageUserId", nativeQuery = true)
		int mSubscribeFollowerCount(int pageUserId);
		
}
