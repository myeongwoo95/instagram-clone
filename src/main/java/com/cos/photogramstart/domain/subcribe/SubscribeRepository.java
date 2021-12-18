package com.cos.photogramstart.domain.subcribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer>{

		@Modifying // insert, delete, update를 네이티브 쿼리로 작성하려면 해당 어노테이션이 필요!!
		@Query( // 네이티브 쿼리는 가급적 사용하지 않는것이 좋다, 복잡한 쿼리나 동적인 쿼리를 작성하기 위한 대안으로 mybatis, QueryDSL(추천)가 있다.
				value = "INSERT INTO subscribe(fromUserId, toUserId, createDate) VALUES(:fromUserId, :toUserId, now())",
				nativeQuery = true)
		// 성공하면 변경된 행의 개수가 리턴, 실패하면 -1, 리턴된 값으로 핸들링 하는건 옛날 방식
		// 반환된 값으로 뭘 하기보다 그냥 예외처리하는게 나음(-1는 터진거니까)
		void mSubscribe(int fromUserId, int toUserId); 
		
		@Modifying
		@Query(
				value = "DELETE FROM subscribe WHERE fromUserId = :fromUserId AND toUserId = :toUserId",
				nativeQuery = true)
		void mUnSubscribe(int fromUserId, int toUserId);
}
