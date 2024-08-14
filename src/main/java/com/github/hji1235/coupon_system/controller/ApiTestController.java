package com.github.hji1235.coupon_system.controller;

import com.github.hji1235.coupon_system.global.ApiResponse;
import com.github.hji1235.coupon_system.controller.dto.TestRequestDto;
import com.github.hji1235.coupon_system.controller.dto.TestResponseDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ApiTestController {

    @GetMapping("/test1")
    public String test1() {
        return "API test1 호출 성공!";
    }

    @PostMapping("/test2/{testId}")
    public ApiResponse<TestResponseDto> test2(
            @PathVariable Long testId,
            @RequestBody TestRequestDto requestDto) {
        System.out.println("testId = " + testId);
        return ApiResponse.success(new TestResponseDto(requestDto.getName(), requestDto.getAge()));
    }

    @GetMapping("/test3")
    public String test3() {
        return "hello 성공!!!!!";
    }
}
