package com.sall.book.springbook.web;

import com.sall.book.springbook.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // 컨트롤러를 JSON을 반환하는 컨트롤러로 만들어준다.
public class HelloController {

    @GetMapping("/hello") // RequestMapping(method = RequestMethod.GET)
    public String Hello() {
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount){
        // RequestParam: 외부에서 API로 넘긴 파라미터를 가져오는 어노테이션
        return new HelloResponseDto(name, amount);
    }

}