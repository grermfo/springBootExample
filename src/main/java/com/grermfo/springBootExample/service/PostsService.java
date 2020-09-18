package com.grermfo.springBootExample.service;

import com.grermfo.springBootExample.domain.posts.Posts;
import com.grermfo.springBootExample.domain.posts.PostsRepository;
import com.grermfo.springBootExample.web.dto.PostsListResponseDto;
import com.grermfo.springBootExample.web.dto.PostsResponseDto;
import com.grermfo.springBootExample.web.dto.PostsSaveRequestDto;
import com.grermfo.springBootExample.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                                     .orElseThrow(() ->new IllegalArgumentException("해당 글이 없음. id = "+ id));
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                                      .orElseThrow(() -> new IllegalArgumentException("해당 글 없음 * id="+id));
        return new PostsResponseDto(entity);
    }

    @Transactional()
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }
}
