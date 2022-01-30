package com.sall.book.springbook.web;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = HelloController.class) // 컨트롤러만 사용하기 때문에 선언함
public class HelloControllerTest {

    @Autowired //스프링이 관리하는 빈 주입받음
    private MockMvc mvc; // 웹 API 테스트 할때 사용 (HTTP GET, POST 등)

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))            // MockMvc를 통해 HTTP GET 요청
                .andExpect(status().isOk())             // mvc.perform 결과를 검증 (status == 200)
                .andExpect(content().string(hello));    // mvc.perform 결과를 검증 (return value == "hello")
    }
}