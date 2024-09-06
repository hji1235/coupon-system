package com.github.hji1235.coupon_system.controller;

import com.github.hji1235.coupon_system.controller.dto.member.MemberLoginRequest;
import com.github.hji1235.coupon_system.controller.dto.member.MemberSaveRequest;
import com.github.hji1235.coupon_system.global.ApiResponse;
import com.github.hji1235.coupon_system.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public ApiResponse<Void> memberSave(@Valid @RequestBody MemberSaveRequest memberSaveRequest) {
        memberService.join(memberSaveRequest);
        return ApiResponse.success();
    }

    @PostMapping("/login")
    public ApiResponse<String> login(@Valid @RequestBody MemberLoginRequest memberLoginRequest) {
        String token = memberService.login(memberLoginRequest);
        return ApiResponse.success(token);
    }
}
