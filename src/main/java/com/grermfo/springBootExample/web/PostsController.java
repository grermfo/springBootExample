package com.grermfo.springBootExample.web;

import com.grermfo.springBootExample.service.PostsService;
import com.grermfo.springBootExample.web.dto.PostsResponseDto;
import com.grermfo.springBootExample.web.dto.PostsSaveRequestDto;
import com.grermfo.springBootExample.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsController {
    private final PostsService postsService;

    @PostMapping("api/s1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    @PutMapping("api/s1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @GetMapping("api/s1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id){
        return postsService.findById(id) ;
    }
}
