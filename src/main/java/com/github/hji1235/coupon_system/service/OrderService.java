package com.github.hji1235.coupon_system.service;

import com.github.hji1235.coupon_system.controller.dto.order.*;
import com.github.hji1235.coupon_system.domain.member.Member;
import com.github.hji1235.coupon_system.domain.order.Order;
import com.github.hji1235.coupon_system.domain.order.OrderMenu;
import com.github.hji1235.coupon_system.domain.store.Menu;
import com.github.hji1235.coupon_system.domain.store.Store;
import com.github.hji1235.coupon_system.global.exception.*;
import com.github.hji1235.coupon_system.repository.*;
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
    private final StoreRepository storeRepository;

    @Transactional
    public void saveOrder(Long storeId, Long memberId, OrderSaveRequest orderSaveRequest) {
        Member member = findMemberById(memberId);
        Store store = findStoreById(storeId);
        Order order = Order.of(member, store);
        orderRepository.save(order);
        List<OrderMenuRequest> orderMenus = orderSaveRequest.getOrderMenus();
        for (OrderMenuRequest dto : orderMenus) {
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

    public List<StoreOrderFindResponse> findAllStoreOrders(Long storeId) {
        return orderRepository.findAllByStoreId(storeId).stream()
                .map(StoreOrderFindResponse::new)
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

    private Store findStoreById(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreNotFoundException(storeId));
    }
}
