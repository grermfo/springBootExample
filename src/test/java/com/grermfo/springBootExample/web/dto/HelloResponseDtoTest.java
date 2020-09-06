package com.grermfo.springBootExample.web.dto;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {

    @Test
    public void lombokFunctionTest() {
        String name = "grermfo";
        int age = 30;

        HelloResponseDto dto = new HelloResponseDto(name, age);

        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAge()).isEqualTo(age);
    }
}
