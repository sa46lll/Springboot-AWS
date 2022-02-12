package com.sall.book.springbook.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest                         // 별다른 설정없이 H2 데이터베이스를 자동으로 실행해준다.
class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @AfterEach                          // 단위 테스트가 끝날 때마다 수해오디는 메소드 지정
    public void cleanup(){              // (보통 배포 전 전체테스트 실행 시 테스트간 데이터 침범 방지)
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기(){
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()   // 테이블 posts에 insert/update 쿼리 실행
            .title(title)                      // id 값이 있으면 update, 없으면 insert 실행
            .content(content)
            .author("sa46lll@nate.com")
            .build());

        // when
        List<Posts> postsList = postsRepository.findAll();      // 테이블 posts에 있는 모든 데이터를 조회

        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);

    }

    @Test
    public void BaseTimeEntity_등록() {
        // given
        LocalDateTime now = LocalDateTime.of(2022, 2, 12, 0, 0, 0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("sa46lll")
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        System.out.println(">>>>>>>> createDate=" + posts.getCreatedDate() +
                ", modifiedDate=" + posts.getModifiedDate());
        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);

    }
}