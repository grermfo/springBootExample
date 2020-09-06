package com.grermfo.springBootExample.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import  static org.hamcrest.Matchers.is;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(controllers =  HelloController.class)
public class HelloControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void returnHello() throws Exception {
        //String helloWorld="hello! world";
        String helloWorld = "Hello! World";
        mvc.perform(get("/helloWorld"))
                .andExpect(status().isOk())
                .andExpect(content().string(helloWorld));
    }

    @Test
    public void returnHelloDto() throws Exception {
        String name = "test";
        int age = 300;

        mvc.perform(get("/helloWorld/dto")
                .param("name",name)
                .param("age",age+"")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(name)))
                .andExpect( jsonPath("$.age",is(age)));

    }


}
