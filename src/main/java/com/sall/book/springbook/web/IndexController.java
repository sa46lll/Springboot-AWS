package com.sall.book.springbook.web;

import com.sall.book.springbook.config.auth.dto.SessionUser;
import com.sall.book.springbook.service.posts.PostsService;
import com.sall.book.springbook.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;


    @GetMapping("/")
    public String index(Model model){   // 가져온 결과를 posts로 index.mustache에 전당
        model.addAttribute("posts", postsService.findAllDesc());

        // CustomOAuth2UserService에서 로그인 성공 시 세션에 SessionUser를 저장
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) { // 세션에 저장된 값이 있을 때만 model에 ㅕㄴㄷ구믇dfh emdfhr
            model.addAttribute("userName", user.getName());
        }
        return "index";                 // src/main/templates/index.mustache
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
