package com.sall.book.springbook.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * LoginUser 어노테이션
 * SessionUser user = (SessionUser) httpSession.getAttribute("user"); -> 개선
 */

@Target(ElementType.PARAMETER)          // 이 어노테이션이 생성될 수 있는 위치 지정
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {
}