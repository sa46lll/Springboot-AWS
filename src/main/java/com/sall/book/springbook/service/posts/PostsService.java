package com.sall.book.springbook.service.posts;

import com.sall.book.springbook.domain.posts.PostsRepository;
import com.sall.book.springbook.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor        // final이 선언된 모든 필드를 인자값으로 하는 생성자 생성 (lombok)
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).
                getId();
    }
}
