package com.sall.book.springbook.web;

import com.sall.book.springbook.config.auth.LoginUser;
import com.sall.book.springbook.config.auth.dto.SessionUser;
import com.sall.book.springbook.service.posts.PostsService;
import com.sall.book.springbook.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;


    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){   // 세션정보를 @LoginUser로 어느 컨트롤러든지 가져올 수 있음
        model.addAttribute("posts", postsService.findAllDesc());

        if (user != null) { // 세션에 저장된 값이 있을 때만
            model.addAttribute("userName", user.getName());
        }
        return "index";     // src/main/templates/index.mustache
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
