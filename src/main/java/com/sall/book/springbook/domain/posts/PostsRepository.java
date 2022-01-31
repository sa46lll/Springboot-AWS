package com.sall.book.springbook.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {      // 상속 -> 기본적인 CRUD 매소드 자동 생성
}
