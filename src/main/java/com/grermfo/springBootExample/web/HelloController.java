package com.grermfo.springBootExample.web;

import com.grermfo.springBootExample.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController  {
    @GetMapping("/helloWorld")
    public String hello() {
        return "Hello! World";
    }

    @GetMapping("/helloWorld/dto")
    public HelloResponseDto helloWorldDto(@RequestParam("name") String name, @RequestParam("age") int age){
        return new HelloResponseDto(name, age);
    }
}
