package com.github.hji1235.coupon_system.test.controller;

import com.github.hji1235.coupon_system.test.dto.TestRequestDto;
import com.github.hji1235.coupon_system.test.dto.TestResponseDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ApiTestController {

    @GetMapping("/test1")
    public String test1() {
        return "test success!";
    }

    @PostMapping("/test2/{testId}")
    public TestResponseDto test2(
            @PathVariable Long testId,
            @RequestBody TestRequestDto requestDto) {
        System.out.println("testId = " + testId);
        return new TestResponseDto(requestDto.getName(), requestDto.getAge());
    }
}
