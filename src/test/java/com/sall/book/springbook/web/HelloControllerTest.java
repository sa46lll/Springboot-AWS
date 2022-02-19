package com.sall.book.springbook.web;


import com.sall.book.springbook.config.auth.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = HelloController.class,    // 인지하지 못하는 Repository, Service, Component를 스캔대상에서 제거
        excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                        classes = SecurityConfig.class)})
public class HelloControllerTest {

    @Autowired //스프링이 관리하는 빈 주입받음
    private MockMvc mvc; // 웹 API 테스트 할때 사용 (HTTP GET, POST 등)

    @Test
    @WithMockUser(roles="USER")
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))            // MockMvc를 통해 HTTP GET 요청
                .andExpect(status().isOk())             // mvc.perform 결과를 검증 (status == 200)
                .andExpect(content().string(hello));    // mvc.perform 결과를 검증 (return value == "hello")
    }

    @Test
    @WithMockUser(roles="USER")
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                    .param("name", name)                            // param: API 테스트랑 때 사용될 요청 파라미터를 설정
                    .param("amount", String.valueOf(amount)))       // 단, 값은 String만 허용 (숫자, 날짜 -> 문자열 변환)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))         // jsonPath: JSON 응답값을 필드별로 검증할 수 있는 메소드
                .andExpect(jsonPath("$.amount", is(amount)));    // $를 기준으로 필드명 명시

    }
}