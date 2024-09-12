package com.github.hji1235.coupon_system.controller.dto.order;

import com.github.hji1235.coupon_system.domain.order.OrderMenu;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class OrderMenuResponse {

    private Long id;
    private String menuName;
    private int quantity;
    private int price;

    public OrderMenuResponse(OrderMenu orderMenu) {
        this.id = orderMenu.getMenu().getId();
        this.menuName = orderMenu.getMenu().getName();
        this.quantity = orderMenu.getQuantity();
        this.price = orderMenu.getUnitPrice();
    }

    public OrderMenuResponse(Long id, String menuName, int quantity, int price) {
        this.id = id;
        this.menuName = menuName;
        this.quantity = quantity;
        this.price = price;
    }
}
