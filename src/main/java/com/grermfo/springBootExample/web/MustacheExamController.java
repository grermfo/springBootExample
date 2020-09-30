package com.grermfo.springBootExample.web;

import com.grermfo.springBootExample.config.auth.dto.SessionUser;
import com.grermfo.springBootExample.service.PostsService;
import com.grermfo.springBootExample.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import javax.servlet.http.HttpSession;
@RequiredArgsConstructor
@Controller
public class MustacheExamController {

    private final PostsService postsService;
    private final HttpSession httpSession;


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user != null)
            model.addAttribute("userName", user.getName());
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
         return "postsSave";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "postsUpdate";
    }

}
