package com.sall.book.springbook.service.posts;

import com.sall.book.springbook.domain.posts.Posts;
import com.sall.book.springbook.domain.posts.PostsRepository;
import com.sall.book.springbook.web.dto.PostsListResponseDto;
import com.sall.book.springbook.web.dto.PostsResponseDto;
import com.sall.book.springbook.web.dto.PostsSaveRequestDto;
import com.sall.book.springbook.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor        // final이 선언된 모든 필드를 인자값으로 하는 생성자 생성 (lombok)
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).
                getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)                 // 조회 기능만 남겨두어 조회 속도 개선
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)     // .map(posts -> new PostsListResponseDto(posts))
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)  // 존재하는 Posts 인지 확인
                .orElseThrow(() -> new IllegalArgumentException("[id:" + id + "]해당 게시글이 없습니다."));
        postsRepository.delete(posts);
    }
}
