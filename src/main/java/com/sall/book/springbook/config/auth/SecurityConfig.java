package com.sall.book.springbook.config.auth;

import com.sall.book.springbook.domain.posts.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity          // Spring Security 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()     // h2-console 화면을 사용하기 위해 해당 옵션들을 disable
                .and()
                .authorizeRequests()                    // URL별 권한 관리를 설정하는 옵션의 시작점, authorizeRequests가 선언되야만 antMatchers 옵션을 시작할 수 있음
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**")      // 권한 관리 대상을 지정하는 옵션 (URL, HTTP 메소드별 관리 가능)
                .permitAll()                     // "/" 등 지정된 URL들은 permitAll() 옵션으로 전체 열람 권한 줌.
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())    // "/api/v1/**" 주소를 가진 API는 USER 권한을 가진 사람만 가능하도록 함.
                .anyRequest().authenticated()    // 설정된 값들 이외 나머지 URL 나타냄 (authenticated(): 나머지 URL들을 모두 인증된 사용자들에게만 허용, 즉 로그인한 사용자)
                .and()
                .logout().logoutSuccessUrl("/")  // 로그아웃 기능에 대한 여러 설정의 진입점, 로그아웃 성공시 / 주소로 이동동
                .and()
                .oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);
                // 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록, 리소스 서버(소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시
    }
}