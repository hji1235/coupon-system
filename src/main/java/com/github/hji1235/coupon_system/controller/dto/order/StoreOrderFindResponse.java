package com.github.hji1235.coupon_system.controller.dto.order;

import com.github.hji1235.coupon_system.domain.order.Order;
import com.github.hji1235.coupon_system.domain.order.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class StoreOrderFindResponse {

    private Long id;
    private LocalDateTime orderAt;
    private OrderStatus orderStatus;
    private List<OrderMenuResponse> orderMenus;

    public StoreOrderFindResponse(Order order) {
        this.id = order.getId();
        this.orderAt = order.getCreatedAt();
        this.orderStatus = order.getOrderStatus();
        this.orderMenus = order.getOrderMenus().stream()
                .map(OrderMenuResponse::new)
                .toList();
    }

    public StoreOrderFindResponse(Long id, LocalDateTime orderAt, OrderStatus orderStatus, List<OrderMenuResponse> orderMenus) {
        this.id = id;
        this.orderAt = orderAt;
        this.orderStatus = orderStatus;
        this.orderMenus = orderMenus;
    }
}
