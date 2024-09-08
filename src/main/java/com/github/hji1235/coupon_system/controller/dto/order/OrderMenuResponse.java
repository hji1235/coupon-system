package com.github.hji1235.coupon_system.controller.dto.order;

import com.github.hji1235.coupon_system.domain.order.OrderMenu;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class OrderMenuResponse {

    private String menuName;
    private int quantity;
    private int price;

    public OrderMenuResponse(OrderMenu orderMenu) {
        this.menuName = orderMenu.getMenu().getName();
        this.quantity = orderMenu.getQuantity();
        this.price = orderMenu.getUnitPrice();
    }
}
