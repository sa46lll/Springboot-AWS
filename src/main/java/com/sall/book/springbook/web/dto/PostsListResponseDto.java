package com.sall.book.springbook.web.dto;

import com.sall.book.springbook.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsListResponseDto {
    private Long id;
    private String title;
    private String author;
    private LocalDateTime modifiedDate;

    public PostsListResponseDto(Posts Entity) {
        this.id = Entity.getId();
        this.title = Entity.getTitle();
        this.author = Entity.getAuthor();
        this.modifiedDate = Entity.getModifiedDate();
    }
}