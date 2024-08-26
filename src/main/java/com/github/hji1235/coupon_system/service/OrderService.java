package com.github.hji1235.coupon_system.service;

import com.github.hji1235.coupon_system.controller.dto.*;
import com.github.hji1235.coupon_system.domain.member.Member;
import com.github.hji1235.coupon_system.domain.order.Order;
import com.github.hji1235.coupon_system.domain.order.OrderMenu;
import com.github.hji1235.coupon_system.domain.store.Menu;
import com.github.hji1235.coupon_system.global.exception.MemberNotFoundException;
import com.github.hji1235.coupon_system.global.exception.MenuNotFoundException;
import com.github.hji1235.coupon_system.global.exception.OrderNotFoundException;
import com.github.hji1235.coupon_system.repository.MemberRepository;
import com.github.hji1235.coupon_system.repository.MenuRepository;
import com.github.hji1235.coupon_system.repository.OrderMenuRepository;
import com.github.hji1235.coupon_system.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final MenuRepository menuRepository;
    private final OrderMenuRepository orderMenuRepository;

    @Transactional
    public void saveOrder(Long memberId, OrderSaveRequest orderSaveRequest) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
        Order order = new Order(member);
        orderRepository.save(order);
        List<OrderMenuDto> orderMenus = orderSaveRequest.getOrderMenus();
        for (OrderMenuDto dto : orderMenus) {
            Menu menu = menuRepository.findById(dto.getMenuId())
                    .orElseThrow(() -> new MenuNotFoundException(dto.getMenuId()));
            OrderMenu orderMenu = new OrderMenu(dto.getQuantity(), menu.getPrice(), order, menu);
            orderMenuRepository.save(orderMenu);
        }
    }

    public OrderFindAllResponse findAllOrders(Long memberId) {
        memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(memberId));
        List<SimpleOrderResponse> simpleOrderResponses = orderRepository.findOrders(memberId).stream()
                .map(SimpleOrderResponse::new).toList();
        return new OrderFindAllResponse(simpleOrderResponses);
    }
}
