package com.github.hji1235.coupon_system.controller.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderFindAllResponse {

    private List<SimpleOrderResponse> orders;

    public OrderFindAllResponse(List<SimpleOrderResponse> orders) {
        this.orders = orders;
    }
}
