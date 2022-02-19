package com.sall.book.springbook.config.auth.dto;

import com.sall.book.springbook.domain.posts.user.User;
import lombok.Getter;

import java.io.Serializable;

/**
 * SessionUser은 인증된 사용자 정보만 필요
 * User 클래스를 사용하지 않는 이유: 세션에 저장하기 위해 User 클래스에 직렬화를 구현하지 않았기 때문..
 */

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}