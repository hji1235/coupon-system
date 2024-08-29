package com.github.hji1235.coupon_system.controller.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderSaveRequest {

    private List<OrderMenuDto> orderMenus;
}
