package com.grermfo.springBootExample.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController  {
    @GetMapping("/helloWorld")
    public String hello() {
        return "Hello! World";
    }
}
