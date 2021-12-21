package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.likes.LikesRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LikeService {
	
	private final LikesRepository likesRepository;
}
