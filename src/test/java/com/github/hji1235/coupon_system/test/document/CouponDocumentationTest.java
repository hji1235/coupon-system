package com.github.hji1235.coupon_system.test.document;

import com.github.hji1235.config.AbstractRestDocsTests;
import com.github.hji1235.coupon_system.controller.CouponController;
import com.github.hji1235.coupon_system.controller.dto.coupon.AdminCouponSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.coupon.StoreCouponDiscountDetailDto;
import com.github.hji1235.coupon_system.controller.dto.coupon.StoreCouponSaveRequest;
import com.github.hji1235.coupon_system.domain.coupon.DiscountType;
import com.github.hji1235.coupon_system.domain.coupon.ExpirationPolicyType;
import com.github.hji1235.coupon_system.domain.coupon.IssuerType;
import com.github.hji1235.coupon_system.service.CouponService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.LocalTime;
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

@WebMvcTest(CouponController.class)
class CouponDocumentationTest extends AbstractRestDocsTests {

    @MockBean
    private CouponService couponService;

    @Test
    @DisplayName("어드민 쿠폰 생성")
    void createAdminCoupon() throws Exception {
        // Given
        AdminCouponSaveRequest adminCouponSaveRequest = AdminCouponSaveRequest.builder()
                .name("론칭 기념 할인 쿠폰")
                .discountAmount(5000)
                .minOrderPrice(15000)
                .maxCount(1000000)
                .maxCountPerMember(1)
                .discountType(DiscountType.FIXED)
                .issuerType(IssuerType.ADMIN)
                .issuerId(0L)
                .expirationPolicyType(ExpirationPolicyType.PERIOD)
                .startAt(LocalDate.of(2024, 9, 13))
                .expiredAt(LocalDate.of(2024, 12, 31))
                .daysFromIssuance(null)
                .timeLimit(true)
                .timeLimitStartAt(LocalTime.of(12,0,0))
                .timeLimitEndAt(LocalTime.of(20, 0, 0))
                .build();
        Mockito.when(couponService.adminCouponSave(ArgumentMatchers.any(AdminCouponSaveRequest.class))).thenReturn(1L);

        // When
        ResultActions result = mockMvc.perform(post("/api/v1/admin/coupons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adminCouponSaveRequest)));

        // Then
        result.andDo(document("{class-name}/{method-name}",
                        preprocessRequest(Preprocessors.prettyPrint()),
                        preprocessResponse(Preprocessors.prettyPrint()),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("쿠폰 이름"),
                                fieldWithPath("discountAmount").type(JsonFieldType.NUMBER).description("할인 금액"),
                                fieldWithPath("minOrderPrice").type(JsonFieldType.NUMBER).optional().description("최소 주문 가격"),
                                fieldWithPath("maxCount").type(JsonFieldType.NUMBER).optional().description("최대 발행 수"),
                                fieldWithPath("maxCountPerMember").type(JsonFieldType.NUMBER).optional().description("고객당 최대 발행 수"),
                                fieldWithPath("discountType").type(JsonFieldType.STRING).description("할인 타입"),
                                fieldWithPath("issuerType").type(JsonFieldType.STRING).description("발행 주체 타입"),
                                fieldWithPath("issuerId").type(JsonFieldType.NUMBER).description("발행 주체 아이디"),
                                fieldWithPath("expirationPolicyType").type(JsonFieldType.STRING).description("유효 기간 정책 타입"),
                                fieldWithPath("startAt").type(JsonFieldType.STRING).optional().description("유효 기간 시작 날짜"),
                                fieldWithPath("expiredAt").type(JsonFieldType.STRING).optional().description("유효 기간 종료 날짜"),
                                fieldWithPath("daysFromIssuance").type(JsonFieldType.NUMBER).optional().description("발급 후 유효 일수"),
                                fieldWithPath("timeLimit").type(JsonFieldType.BOOLEAN).description("시간 제한 여부"),
                                fieldWithPath("timeLimitStartAt").type(JsonFieldType.STRING).optional().description("시간 제한 시작 시간"),
                                fieldWithPath("timeLimitEndAt").type(JsonFieldType.STRING).optional().description("시간 제한 종료 시간")
                        )
                ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", is(1)))
                .andExpect(jsonPath("$.error", is(nullValue())));
    }

    @Test
    @DisplayName("스토어 쿠폰 생성")
    void createStoreCoupons() throws Exception {
        // Given
        Long storeId = 1L;
        StoreCouponSaveRequest storeCouponSaveRequest = StoreCouponSaveRequest.builder()
                .name("네네치킨 시청점 상시 할인 쿠폰")
                .maxCount(null)
                .maxCountPerMember(null)
                .discountType(DiscountType.FIXED)
                .expirationPolicyType(ExpirationPolicyType.AFTER_ISSUE_DATE)
                .startAt(null)
                .expiredAt(null)
                .daysFromIssuance(14)
                .timeLimit(false)
                .timeLimitStartAt(null)
                .timeLimitEndAt(null)
                .discountDetails(List.of(
                        new StoreCouponDiscountDetailDto(1000, 15000),
                        new StoreCouponDiscountDetailDto(2000, 20000),
                        new StoreCouponDiscountDetailDto(3000, 30000)
                ))
                .build();

        // When
        ResultActions result = mockMvc.perform(post("/api/v1/stores/{storeId}/coupons", storeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(storeCouponSaveRequest)));

        // Then
        result.andDo(document("{class-name}/{method-name}",
                        preprocessRequest(Preprocessors.prettyPrint()),
                        preprocessResponse(Preprocessors.prettyPrint()),
                        pathParameters(
                                parameterWithName("storeId").description("스토어 아이디")
                        ),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("쿠폰 이름"),
                                fieldWithPath("maxCount").type(JsonFieldType.NUMBER).optional().description("최대 발행 수"),
                                fieldWithPath("maxCountPerMember").type(JsonFieldType.NUMBER).optional().description("고객당 최대 발행 수"),
                                fieldWithPath("discountType").type(JsonFieldType.STRING).description("할인 타입"),
                                fieldWithPath("expirationPolicyType").type(JsonFieldType.STRING).description("유효 기간 정책 타입"),
                                fieldWithPath("startAt").type(JsonFieldType.STRING).optional().description("유효 기간 시작 날짜"),
                                fieldWithPath("expiredAt").type(JsonFieldType.STRING).optional().description("유효 기간 종료 날짜"),
                                fieldWithPath("daysFromIssuance").type(JsonFieldType.NUMBER).optional().description("발급 후 유효 일수"),
                                fieldWithPath("timeLimit").type(JsonFieldType.BOOLEAN).description("시간 제한 여부"),
                                fieldWithPath("timeLimitStartAt").type(JsonFieldType.STRING).optional().description("시간 제한 시작 시간"),
                                fieldWithPath("timeLimitEndAt").type(JsonFieldType.STRING).optional().description("시간 제한 종료 시간"),
                                fieldWithPath("discountDetails[].discountAmount").type(JsonFieldType.NUMBER).description("쿠폰 할인 금액"),
                                fieldWithPath("discountDetails[].minOrderPrice").type(JsonFieldType.NUMBER).description("쿠폰 최소 주문 가격")
                        )
                ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", is(nullValue())))
                .andExpect(jsonPath("$.error", is(nullValue())));
    }
}
