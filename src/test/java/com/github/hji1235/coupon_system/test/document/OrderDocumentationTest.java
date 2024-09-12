package com.github.hji1235.coupon_system.test.document;

import com.github.hji1235.config.AbstractRestDocsTests;
import com.github.hji1235.coupon_system.controller.OrderController;
import com.github.hji1235.coupon_system.controller.dto.CustomUserInfoDto;
import com.github.hji1235.coupon_system.controller.dto.order.*;
import com.github.hji1235.coupon_system.domain.member.Member;
import com.github.hji1235.coupon_system.domain.member.Role;
import com.github.hji1235.coupon_system.domain.order.OrderStatus;
import com.github.hji1235.coupon_system.global.jwt.CustomUserDetails;
import com.github.hji1235.coupon_system.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderDocumentationTest extends AbstractRestDocsTests {

    @MockBean
    private OrderService orderService;

    @BeforeEach
    void beforeEach() {
        Member member = Member.of("hji1235@naver.com", "456456456", "CMH", Role.MEMBER);
        CustomUserInfoDto customUserInfoDto = new CustomUserInfoDto(member);
        customUserInfoDto.setId(1L);
        CustomUserDetails customUserDetails = new CustomUserDetails(customUserInfoDto);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities())
        );
    }

    @Test
    @DisplayName("주문 생성")
    void createOrder() throws Exception {
        // Given
        Long storeId = 1L;
        OrderSaveRequest orderSaveRequest = new OrderSaveRequest(List.of(
                new OrderMenuRequest(1L, 2),
                new OrderMenuRequest(2L, 1),
                new OrderMenuRequest(3L, 3)
        ));

        // When
        ResultActions result = mockMvc.perform(post("/api/v1/stores/{storeId}/orders", storeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderSaveRequest)));

        // Then
        result.andDo(document("{class-name}/{method-name}",
                        preprocessRequest(Preprocessors.prettyPrint()),
                        preprocessResponse(Preprocessors.prettyPrint()),
                        pathParameters(
                                parameterWithName("storeId").description("스토어 아이디")
                        ),
                        requestFields(
                                fieldWithPath("orderMenus[].menuId").type(JsonFieldType.NUMBER).description("메뉴 아이디"),
                                fieldWithPath("orderMenus[].quantity").type(JsonFieldType.NUMBER).description("수량")
                        )
                ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", is(nullValue())))
                .andExpect(jsonPath("$.error", is(nullValue())));
    }

    @Test
    @DisplayName("주문 상세 조회")
    void getOrder() throws Exception {
        // Given
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long memberId = userDetails.getMemberId();
        Long orderId = 1L;
        OrderFindResponse orderFindResponse = new OrderFindResponse("네네치킨-시청점", LocalDateTime.of(2024, 9, 13, 12, 0, 0, 0),
                List.of(new OrderMenuResponse(1L, "후라이드 치킨", 2, 15000),
                        new OrderMenuResponse(2L, "양념 치킨", 1, 17000),
                        new OrderMenuResponse(3L, "콜라", 3, 2000)),
                53000, 2000, "네네치킨 시청점 할인 쿠폰");
        Mockito.when(orderService.findOrder(orderId, memberId)).thenReturn(orderFindResponse);

        // When
        ResultActions result = mockMvc.perform(get("/api/v1/orders/{orderId}", orderId)
                .accept(MediaType.APPLICATION_JSON));

        // Then
        result.andDo(document("{class-name}/{method-name}",
                        preprocessRequest(Preprocessors.prettyPrint()),
                        preprocessResponse(Preprocessors.prettyPrint()),
                        pathParameters(
                                parameterWithName("orderId").description("주문 아이디")
                        ),
                        responseFields(
                                beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("storeName").type(JsonFieldType.STRING).description("스토어 이름"),
                                fieldWithPath("orderAt").type(JsonFieldType.STRING).description("주문 일시"),
                                fieldWithPath("orderMenus[].id").type(JsonFieldType.NUMBER).description("메뉴 아이디"),
                                fieldWithPath("orderMenus[].menuName").type(JsonFieldType.STRING).description("메뉴 이름"),
                                fieldWithPath("orderMenus[].quantity").type(JsonFieldType.NUMBER).description("메뉴 수량"),
                                fieldWithPath("orderMenus[].price").type(JsonFieldType.NUMBER).description("메뉴 가격"),
                                fieldWithPath("paymentAmount").type(JsonFieldType.NUMBER).description("결제 금액"),
                                fieldWithPath("discountAmount").type(JsonFieldType.NUMBER).description("할인 금액"),
                                fieldWithPath("couponName").type(JsonFieldType.STRING).description("쿠폰 이름")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.storeName", is("네네치킨-시청점")))
                .andExpect(jsonPath("$.data.orderAt", is("2024-09-13T12:00:00")))
                .andExpect(jsonPath("$.data.paymentAmount", is(53000)))
                .andExpect(jsonPath("$.data.discountAmount", is(2000)))
                .andExpect(jsonPath("$.data.couponName", is("네네치킨 시청점 할인 쿠폰")))
                .andExpect(jsonPath("$.data.orderMenus[0].id", is(1)))
                .andExpect(jsonPath("$.data.orderMenus[0].menuName", is("후라이드 치킨")))
                .andExpect(jsonPath("$.data.orderMenus[0].quantity", is(2)))
                .andExpect(jsonPath("$.data.orderMenus[0].price", is(15000)))
                .andExpect(jsonPath("$.data.orderMenus[1].id", is(2)))
                .andExpect(jsonPath("$.data.orderMenus[1].menuName", is("양념 치킨")))
                .andExpect(jsonPath("$.data.orderMenus[1].quantity", is(1)))
                .andExpect(jsonPath("$.data.orderMenus[1].price", is(17000)))
                .andExpect(jsonPath("$.data.orderMenus[2].id", is(3)))
                .andExpect(jsonPath("$.data.orderMenus[2].menuName", is("콜라")))
                .andExpect(jsonPath("$.data.orderMenus[2].quantity", is(3)))
                .andExpect(jsonPath("$.data.orderMenus[2].price", is(2000)))
                .andExpect(jsonPath("$.error", is(nullValue())));
    }

    @Test
    @DisplayName("주문 리스트 조회")
    void getAllOrders() throws Exception {
        // Given
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long memberId = userDetails.getMemberId();
        List<SimpleOrderResponse> simpleOrderResponses = List.of(
                new SimpleOrderResponse(1L, LocalDateTime.of(2024, 9, 1, 12, 40, 0, 0),
                        "네네치킨-시청점", OrderStatus.COMPLETED),
                new SimpleOrderResponse(2L, LocalDateTime.of(2024, 9, 10, 12, 40, 0, 0),
                        "네네치킨-시청점", OrderStatus.COMPLETED),
                new SimpleOrderResponse(3L, LocalDateTime.of(2024, 9, 13, 12, 40, 0, 0),
                        "네네치킨-시청점", OrderStatus.PREPARING)
        );
        Mockito.when(orderService.findAllOrders(memberId)).thenReturn(simpleOrderResponses);

        // When
        ResultActions result = mockMvc.perform(get("/api/v1/orders")
                .accept(MediaType.APPLICATION_JSON));

        // Then
        result.andDo(document("{class-name}/{method-name}",
                        preprocessRequest(Preprocessors.prettyPrint()),
                        preprocessResponse(Preprocessors.prettyPrint()),
                        responseFields(
                                beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("주문 아이디"),
                                fieldWithPath("orderAt").type(JsonFieldType.STRING).description("주문 일시"),
                                fieldWithPath("storeName").type(JsonFieldType.STRING).description("스토어 이름"),
                                fieldWithPath("orderStatus").type(JsonFieldType.STRING).description("주문 상태")
                        )
                ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[0].orderAt", is("2024-09-01T12:40:00")))
                .andExpect(jsonPath("$.data[0].storeName", is("네네치킨-시청점")))
                .andExpect(jsonPath("$.data[0].orderStatus", is("COMPLETED")))
                .andExpect(jsonPath("$.data[1].id", is(2)))
                .andExpect(jsonPath("$.data[1].orderAt", is("2024-09-10T12:40:00")))
                .andExpect(jsonPath("$.data[1].storeName", is("네네치킨-시청점")))
                .andExpect(jsonPath("$.data[1].orderStatus", is("COMPLETED")))
                .andExpect(jsonPath("$.data[2].id", is(3)))
                .andExpect(jsonPath("$.data[2].orderAt", is("2024-09-13T12:40:00")))
                .andExpect(jsonPath("$.data[2].storeName", is("네네치킨-시청점")))
                .andExpect(jsonPath("$.data[2].orderStatus", is("PREPARING")))
                .andExpect(jsonPath("$.error", is(nullValue())));
    }

    @Test
    @DisplayName("특정 가게의 주문 리스트 조회")
    void getAllStoreOrders() throws Exception {
        // Given
        Long storeId = 1L;
        List<StoreOrderFindResponse> storeOrderFindResponses = List.of(
                new StoreOrderFindResponse(1L, LocalDateTime.of(2024, 9, 15, 13, 0, 0, 0), OrderStatus.PREPARING,
                        List.of(new OrderMenuResponse(1L, "후라이드 치킨", 1, 15000))),
                new StoreOrderFindResponse(2L, LocalDateTime.of(2024, 9, 15, 13, 0, 0, 0), OrderStatus.PREPARING,
                        List.of(new OrderMenuResponse(2L, "양념 치킨", 1, 17000))),
                new StoreOrderFindResponse(3L, LocalDateTime.of(2024, 9, 15, 13, 30, 0, 0), OrderStatus.PENDING,
                        List.of(new OrderMenuResponse(2L, "양념 치킨", 2, 17000)))
        );
        Mockito.when(orderService.findAllStoreOrders(storeId)).thenReturn(storeOrderFindResponses);

        // When
        ResultActions result = mockMvc.perform(get("/api/v1/stores/{storeId}/orders", storeId)
                .accept(MediaType.APPLICATION_JSON));

        // Then
        result.andDo(document("{class-name}/{method-name}",
                        preprocessRequest(Preprocessors.prettyPrint()),
                        preprocessResponse(Preprocessors.prettyPrint()),
                        pathParameters(
                                parameterWithName("storeId").description("스토어 아이디")
                        ),
                        responseFields(
                                beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("주문 아이디"),
                                fieldWithPath("orderAt").type(JsonFieldType.STRING).description("주문 일시"),
                                fieldWithPath("orderStatus").type(JsonFieldType.STRING).description("주문 상태"),
                                fieldWithPath("orderMenus[].id").type(JsonFieldType.NUMBER).description("메뉴 아이디"),
                                fieldWithPath("orderMenus[].menuName").type(JsonFieldType.STRING).description("메뉴 이름"),
                                fieldWithPath("orderMenus[].quantity").type(JsonFieldType.NUMBER).description("메뉴 수량"),
                                fieldWithPath("orderMenus[].price").type(JsonFieldType.NUMBER).description("메뉴 가격")
                        )
                ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[0].orderAt", is("2024-09-15T13:00:00")))
                .andExpect(jsonPath("$.data[0].orderStatus", is("PREPARING")))
                .andExpect(jsonPath("$.data[0].orderMenus[0].id", is(1)))
                .andExpect(jsonPath("$.data[0].orderMenus[0].menuName", is("후라이드 치킨")))
                .andExpect(jsonPath("$.data[0].orderMenus[0].quantity", is(1)))
                .andExpect(jsonPath("$.data[0].orderMenus[0].price", is(15000)))
                .andExpect(jsonPath("$.data[1].id", is(2)))
                .andExpect(jsonPath("$.data[1].orderAt", is("2024-09-15T13:00:00")))
                .andExpect(jsonPath("$.data[1].orderStatus", is("PREPARING")))
                .andExpect(jsonPath("$.data[1].orderMenus[0].id", is(2)))
                .andExpect(jsonPath("$.data[1].orderMenus[0].menuName", is("양념 치킨")))
                .andExpect(jsonPath("$.data[1].orderMenus[0].quantity", is(1)))
                .andExpect(jsonPath("$.data[1].orderMenus[0].price", is(17000)))
                .andExpect(jsonPath("$.data[2].id", is(3)))
                .andExpect(jsonPath("$.data[2].orderAt", is("2024-09-15T13:30:00")))
                .andExpect(jsonPath("$.data[2].orderStatus", is("PENDING")))
                .andExpect(jsonPath("$.data[2].orderMenus[0].id", is(2)))
                .andExpect(jsonPath("$.data[2].orderMenus[0].menuName", is("양념 치킨")))
                .andExpect(jsonPath("$.data[2].orderMenus[0].quantity", is(2)))
                .andExpect(jsonPath("$.data[2].orderMenus[0].price", is(17000)))
                .andExpect(jsonPath("$.error", is(nullValue())));
    }

    @Test
    @DisplayName("주문 수락(가게)")
    void updateOrderStatusToPreparing() throws Exception {
        // Given
        Long orderId = 1L;

        // When
        ResultActions result = mockMvc.perform(patch("/api/v1/orders/{orderId}/accept", orderId)
                .accept(MediaType.APPLICATION_JSON));

        // Then
        result.andDo(document("{class-name}/{method-name}",
                        preprocessRequest(Preprocessors.prettyPrint()),
                        preprocessResponse(Preprocessors.prettyPrint()),
                        pathParameters(
                                parameterWithName("orderId").description("주문 아이디")
                        )
                ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", is(nullValue())))
                .andExpect(jsonPath("$.error", is(nullValue())));
    }

    @Test
    @DisplayName("주문 완료(가게)")
    void updateOrderStatusToComplete() throws Exception {
        // Given
        Long orderId = 1L;

        // When
        ResultActions result = mockMvc.perform(patch("/api/v1/orders/{orderId}/complete", orderId)
                .accept(MediaType.APPLICATION_JSON));

        // Then
        result.andDo(document("{class-name}/{method-name}",
                        preprocessRequest(Preprocessors.prettyPrint()),
                        preprocessResponse(Preprocessors.prettyPrint()),
                        pathParameters(
                                parameterWithName("orderId").description("주문 아이디")
                        )
                ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", is(nullValue())))
                .andExpect(jsonPath("$.error", is(nullValue())));
    }
}
