package com.cos.photogramstart.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

//어노테이션이 없어도 자동으로 JpaRepository를 상속하면 자동으로 IoC 등록이 된다.
public interface UserRepository extends JpaRepository<User, Integer>{ // 2번째 파라미터는 Entity의 id primary key의 타입을 써준다.
	//JPA creation query from method names 
	User findByUsername(String username);
}
