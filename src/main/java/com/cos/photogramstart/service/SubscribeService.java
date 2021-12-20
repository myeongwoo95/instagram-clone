package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subcribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {
	
	private final SubscribeRepository subscribeRepository;
	
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
