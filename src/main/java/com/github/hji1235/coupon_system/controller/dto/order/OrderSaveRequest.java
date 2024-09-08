package com.github.hji1235.coupon_system.controller.dto.order;

import com.github.hji1235.coupon_system.controller.dto.order.OrderMenuDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderSaveRequest {

    @Valid
    @Size(min = 1)
    private List<OrderMenuDto> orderMenus;
}
