package com.github.hji1235.coupon_system.controller.dto.order;

import com.github.hji1235.coupon_system.domain.order.Order;
import com.github.hji1235.coupon_system.domain.order.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SimpleOrderResponse {

    private Long id;
    private LocalDateTime orderAt;
    private String storeName;
    private OrderStatus orderStatus;

    public SimpleOrderResponse(Order order) {
        this.id = order.getId();
        this.orderAt = order.getCreatedAt();
        this.storeName = order.getStore().getName();
        this.orderStatus = order.getOrderStatus();
    }

    public SimpleOrderResponse(Long id, LocalDateTime orderAt, String storeName, OrderStatus orderStatus) {
        this.id = id;
        this.orderAt = orderAt;
        this.storeName = storeName;
        this.orderStatus = orderStatus;
    }
}
