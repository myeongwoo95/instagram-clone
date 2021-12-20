package com.cos.photogramstart.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subcribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {
	
	private final SubscribeRepository subscribeRepository;
	private final EntityManager em; // 모든 Repository EntityManager를 구현해서 만들어져있는 구현체이다. 
	
	@Transactional(readOnly = true)
	public List<SubscribeDto> 구독리스트(int principalId, int pageUserId) {
		
		// 쿼리준비
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT u.id, u.username, u.profileImageUrl, ");
		sb.append("if ((SELECT TRUE FROM subscribe WHERE fromUserId = ? AND toUserId = u.id), 1, 0) subscribeState, ");
		sb.append("if ((?=u.id), 1, 0) equalUserState ");
		sb.append("FROM user u INNER JOIN subscribe s ");
		sb.append("ON u.id = s.toUserId ");
		sb.append("WHERE s.fromUserId = ?"); // 세미콜런 첨부하면 안됨
		
		// 쿼리완성
		Query query = em.createNativeQuery(sb.toString())// java.persistence.Query
				.setParameter(1, principalId)
				.setParameter(2, principalId)
				.setParameter(3, pageUserId);
				
		// 쿼리실행
		JpaResultMapper result = new JpaResultMapper();
		List<SubscribeDto> subscribeDtos = result.list(query, SubscribeDto.class);
		// result.uniqueResult: 단일 데이터(객체포함)
		// result.list (리스트)
	
		return subscribeDtos;
	}
	
	@Transactional
	public void 구독하기(int fromUserid, int toUserId) {
		try {
			subscribeRepository.mSubscribe(fromUserid, toUserId);
		} catch (Exception e) {
			throw new CustomApiException("에러가 발생하였습니다.");
		}
	}
	
	@Transactional
	public void 구독취소하기(int fromUserid, int toUserId) {
		try {
			subscribeRepository.mUnSubscribe(fromUserid, toUserId); 
		} catch (Exception e) {
			throw new CustomApiException("에러가 발생하였습니다.");
		}
	}

}
