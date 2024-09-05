package com.github.hji1235.coupon_system.service;

import com.github.hji1235.coupon_system.controller.dto.*;
import com.github.hji1235.coupon_system.domain.member.Member;
import com.github.hji1235.coupon_system.domain.order.Order;
import com.github.hji1235.coupon_system.domain.order.OrderMenu;
import com.github.hji1235.coupon_system.domain.store.Menu;
import com.github.hji1235.coupon_system.global.exception.MemberNotFoundException;
import com.github.hji1235.coupon_system.global.exception.MenuNotBelongToStoreException;
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
    public void saveOrder(Long storeId, Long memberId, OrderSaveRequest orderSaveRequest) {
        Member member = findMemberById(memberId);
        Order order = Order.of(member);
        orderRepository.save(order);
        List<OrderMenuDto> orderMenus = orderSaveRequest.getOrderMenus();
        for (OrderMenuDto dto : orderMenus) {
            Menu menu = findMenuByIdWithStore(dto.getMenuId());
            if (!storeId.equals(menu.getStore().getId())) {
                throw new MenuNotBelongToStoreException(menu.getId(), storeId);
            }
            OrderMenu orderMenu = OrderMenu.of(dto.getQuantity(), menu.getPrice(), order, menu);
            orderMenuRepository.save(orderMenu);
        }
    }

    public OrderFindResponse findOrder(Long orderId, Long memberId) {
        Order order = findByOrderAndMemberWithPayment(orderId, memberId);
        List<OrderMenu> orderMenus = orderMenuRepository.findByOrderId(orderId);
        return new OrderFindResponse(order, orderMenus);
    }

    public List<SimpleOrderResponse> findAllOrders(Long memberId) {
        return orderRepository.findAllByMemberId(memberId).stream()
                .map(SimpleOrderResponse::new)
                .toList();
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    private Menu findMenuByIdWithStore(Long menuId) {
        return menuRepository.findByIdWithStore(menuId)
                .orElseThrow(() -> new MenuNotFoundException(menuId));
    }

    private Order findByOrderAndMemberWithPayment(Long orderId, Long memberId) {
        return orderRepository.findByOrderAndMemberWithPayment(orderId, memberId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }
}
