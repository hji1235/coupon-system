package com.github.hji1235.coupon_system.controller.dto;

import com.github.hji1235.coupon_system.domain.order.Order;
import com.github.hji1235.coupon_system.domain.order.OrderMenu;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderFindResponse {

    private String storeName;
    private LocalDateTime orderAt;
    private List<OrderMenuResponse> orderMenus;
    private int paymentAmount;
    private int discountAmount;
    private String couponName;

    public OrderFindResponse(Order order, List<OrderMenu> orderMenus) {
        this.storeName = orderMenus.get(0).getMenu().getStore().getName();
        this.orderAt = order.getCreatedAt();
        this.orderMenus = orderMenus.stream().map(OrderMenuResponse::new).toList();
        this.paymentAmount = order.getPayment().getPaymentAmount();
        this.discountAmount = order.getPayment().getDiscountAmount();
        if (order.getPayment().getMemberCoupon() != null) {
            this.couponName = order.getPayment().getMemberCoupon().getCoupon().getName();
        }
    }
}
