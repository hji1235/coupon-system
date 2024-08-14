package com.github.hji1235.coupon_system.controller.dto;

import lombok.Data;

@Data
public class TestResponseDto {

    private String name;

    private int age;

    public TestResponseDto(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
