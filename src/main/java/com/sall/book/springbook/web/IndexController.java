package com.sall.book.springbook.web;

import com.sall.book.springbook.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model){   // 가져온 결과를 posts로 index.mustache에 전당
        model.addAttribute("posts", postsService.findAllDesc());
        return "index";                 // src/main/templates/index.mustache
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }


}
