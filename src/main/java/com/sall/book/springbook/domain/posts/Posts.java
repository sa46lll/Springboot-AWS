package com.sall.book.springbook.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor                                          // 기본 생성자 자동 추가 == public Posts(){}
@Entity                                                     // 테이블과 링크될 클래스임을 나타냄. (주로 카멜케이스 표기법 SalesManager.java -> sales_manager table)
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // PK 생성 규칙 (스프링부트 2.0에서는 IDENTITY 옵션을 추가해야만 auto_increment 적용)
    private Long id;

    @Column(length = 500, nullable = false)                 // 테이블의 칼럼, 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 칼럼 (문자열의 경우 varchar(255)가 기본값)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
