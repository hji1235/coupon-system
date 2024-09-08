package com.github.hji1235.coupon_system.controller.dto.order;

import com.github.hji1235.coupon_system.domain.order.Order;
import com.github.hji1235.coupon_system.domain.order.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SimpleOrderResponse {

    private LocalDateTime orderAt;
    private String storeName;
    private OrderStatus orderStatus;

    public SimpleOrderResponse(Order order) {
        this.orderAt = order.getLastModifiedAt();
        this.storeName = order.getStore().getName();
        this.orderStatus = order.getOrderStatus();
    }
}
