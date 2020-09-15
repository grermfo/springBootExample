package com.grermfo.springBootExample.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    private static final Logger log = LogManager.getLogger(PostsRepositoryTest.class);

    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void selectList() {
        String title ="테스트 글";
        String content ="테스트 본문";

        postsRepository.save(Posts.builder()
            .title(title)
            .content(content)
            .author("writer")
            .build());
        List<Posts> postsList = postsRepository.findAll();

        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
    }

    @Test
    public void BaseTimeEntityRegist() {
        LocalDateTime now = LocalDateTime.of(2019, 9,15,18,14,0 );
        postsRepository.save(Posts.builder()
                .title("dateTitle")
                .content("dateContent")
                .author("dateAuthor")
                .build());

        List<Posts> postsList = postsRepository.findAll();

        Posts posts = postsList.get(0);

        log.info("******  createDate : " + posts.getCreateDate() + " ****** modifiedDate= " + posts.getModifiedDate());

        assertThat(posts.getCreateDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);

    }
}
