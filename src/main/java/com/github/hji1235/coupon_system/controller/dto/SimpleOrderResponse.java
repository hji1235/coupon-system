package com.github.hji1235.coupon_system.controller.dto;

import com.github.hji1235.coupon_system.domain.order.Order;
import com.github.hji1235.coupon_system.domain.order.OrderStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SimpleOrderResponse {

    private LocalDateTime orderDate;
    private String storeName;
    private OrderStatus orderStatus;

    public SimpleOrderResponse(Order order) {
        this.orderDate = order.getLastModifiedAt();
        this.storeName = order.getStore().getName();
        this.orderStatus = order.getOrderStatus();
    }
}
