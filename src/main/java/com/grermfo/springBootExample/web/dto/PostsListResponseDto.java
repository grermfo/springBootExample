package com.grermfo.springBootExample.web.dto;

import com.grermfo.springBootExample.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsListResponseDto {
    private String title;
    private String content;
    private String author;
    private Long id;
    private LocalDateTime modifyedDate;


    public PostsListResponseDto(Posts entity)
    {
        this.title = entity.getTitle();
        this.content= entity.getContent();
        this.author = entity.getAuthor();
        this.id = entity.getId();
       // this.modifyedDate = entity.getModifiedDate();
    }
}
