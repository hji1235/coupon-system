package com.github.hji1235.coupon_system.test.document;

import com.github.hji1235.config.AbstractRestDocsTests;
import com.github.hji1235.coupon_system.controller.MemberCouponController;
import com.github.hji1235.coupon_system.controller.dto.CustomUserInfoDto;
import com.github.hji1235.coupon_system.controller.dto.coupon.MemberCouponAllocateRequest;
import com.github.hji1235.coupon_system.controller.dto.coupon.MemberCouponAvailableCheckResponse;
import com.github.hji1235.coupon_system.controller.dto.coupon.MemberCouponCodeSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.coupon.MemberCouponFindResponse;
import com.github.hji1235.coupon_system.domain.coupon.DiscountType;
import com.github.hji1235.coupon_system.domain.member.Member;
import com.github.hji1235.coupon_system.domain.member.Role;
import com.github.hji1235.coupon_system.global.jwt.CustomUserDetails;
import com.github.hji1235.coupon_system.service.MemberCouponService;
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

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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

@WebMvcTest(MemberCouponController.class)
class MemberCouponDocumentationTest extends AbstractRestDocsTests {

    @MockBean
    private MemberCouponService memberCouponService;

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
    @DisplayName("미할당 쿠폰 생성(쿠폰 코드 생성)")
    void issueCouponCodes() throws Exception {
        // Given
        Long couponId = 1L;
        MemberCouponCodeSaveRequest memberCouponCodeSaveRequest = new MemberCouponCodeSaveRequest(100);

        // When
        ResultActions result = mockMvc.perform(post("/api/v1/coupons/{couponId}/member-coupons/issue-code", couponId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberCouponCodeSaveRequest)));

        // Then
        result.andDo(document("{class-name}/{method-name}",
                        preprocessRequest(Preprocessors.prettyPrint()),
                        preprocessResponse(Preprocessors.prettyPrint()),
                        pathParameters(
                                parameterWithName("couponId").description("쿠폰 아이디")
                        ),
                        requestFields(
                                fieldWithPath("quantity").type(JsonFieldType.NUMBER).description("수량")
                        )
                ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", is(nullValue())))
                .andExpect(jsonPath("$.error", is(nullValue())));
    }

    @Test
    @DisplayName("쿠폰 할당(클릭)")
    void allocateMemberCouponByClick() throws Exception {
        // Given
        Long couponId = 1L;

        // When
        ResultActions result = mockMvc.perform(post("/api/v1//coupons/{couponId}/member-coupons/allocate-click", couponId)
                .accept(MediaType.APPLICATION_JSON));

        // Then
        result.andDo(document("{class-name}/{method-name}",
                        preprocessRequest(Preprocessors.prettyPrint()),
                        preprocessResponse(Preprocessors.prettyPrint()),
                        pathParameters(
                                parameterWithName("couponId").description("쿠폰 아이디")
                        )
                ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", is(nullValue())))
                .andExpect(jsonPath("$.error", is(nullValue())));
    }

    @Test
    @DisplayName("쿠폰 할당(코드)")
    void allocateMemberCouponByCode() throws Exception {
        // Given
        UUID couponCode = UUID.randomUUID();
        MemberCouponAllocateRequest memberCouponAllocateRequest = new MemberCouponAllocateRequest(couponCode);

        // When
        ResultActions result = mockMvc.perform(patch("/api/v1/member-coupons/allocate-code")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberCouponAllocateRequest))
                .accept(MediaType.APPLICATION_JSON));

        // Then
        result.andDo(document("{class-name}/{method-name}",
                        preprocessRequest(Preprocessors.prettyPrint()),
                        preprocessResponse(Preprocessors.prettyPrint()),
                        requestFields(
                                fieldWithPath("couponCode").type(JsonFieldType.STRING).description("쿠폰 코드")
                        )
                ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", is(nullValue())))
                .andExpect(jsonPath("$.error", is(nullValue())));
    }

    @Test
    @DisplayName("멤버 쿠폰 조회")
    void getAllMemberCoupons() throws Exception {
        // Given
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long memberId = userDetails.getMemberId();
        List<MemberCouponFindResponse> memberCouponFindResponses = List.of(
                new MemberCouponFindResponse(DiscountType.FIXED, 1000, "네네치킨 시청점 할인 쿠폰", LocalDate.of(2024, 12, 31), 15000),
                new MemberCouponFindResponse(DiscountType.FIXED, 2000, "네네치킨 시청점 할인 쿠폰", LocalDate.of(2024, 12, 31), 20000),
                new MemberCouponFindResponse(DiscountType.PERCENT, 10, "치킨대전 이벤트 10% 할인 쿠폰", LocalDate.of(2024, 11, 15), 20000)
        );
        Mockito.when(memberCouponService.findAllMemberCoupons(memberId)).thenReturn(memberCouponFindResponses);

        // When
        ResultActions result = mockMvc.perform(get("/api/v1/member-coupons")
                .accept(MediaType.APPLICATION_JSON));

        // Then
        result.andDo(document("{class-name}/{method-name}",
                        preprocessRequest(Preprocessors.prettyPrint()),
                        preprocessResponse(Preprocessors.prettyPrint()),
                        responseFields(
                                beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("discountType").type(JsonFieldType.STRING).description("할인 타입"),
                                fieldWithPath("discountAmount").type(JsonFieldType.NUMBER).description("할인 금액"),
                                fieldWithPath("couponName").type(JsonFieldType.STRING).description("쿠폰 이름"),
                                fieldWithPath("expiredAt").type(JsonFieldType.STRING).description("유효 기간 종료 날짜"),
                                fieldWithPath("minOrderPrice").type(JsonFieldType.NUMBER).description("최소 주문 가격")
                        )
                ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].discountType", is("FIXED")))
                .andExpect(jsonPath("$.data[0].discountAmount", is(1000)))
                .andExpect(jsonPath("$.data[0].couponName", is("네네치킨 시청점 할인 쿠폰")))
                .andExpect(jsonPath("$.data[0].expiredAt", is("2024-12-31")))
                .andExpect(jsonPath("$.data[0].minOrderPrice", is(15000)))
                .andExpect(jsonPath("$.data[1].discountType", is("FIXED")))
                .andExpect(jsonPath("$.data[1].discountAmount", is(2000)))
                .andExpect(jsonPath("$.data[1].couponName", is("네네치킨 시청점 할인 쿠폰")))
                .andExpect(jsonPath("$.data[1].expiredAt", is("2024-12-31")))
                .andExpect(jsonPath("$.data[1].minOrderPrice", is(20000)))
                .andExpect(jsonPath("$.data[2].discountType", is("PERCENT")))
                .andExpect(jsonPath("$.data[2].discountAmount", is(10)))
                .andExpect(jsonPath("$.data[2].couponName", is("치킨대전 이벤트 10% 할인 쿠폰")))
                .andExpect(jsonPath("$.data[2].expiredAt", is("2024-11-15")))
                .andExpect(jsonPath("$.data[2].minOrderPrice", is(20000)));
    }

    @Test
    @DisplayName("주문 중 사용 가능 쿠폰 조회")
    void getAllAvailableMemberCoupons() throws Exception {
        // Given
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long memberId = userDetails.getMemberId();
        Long storeId = 1L;
        Long orderId = 1L;
        List<MemberCouponAvailableCheckResponse> memberCouponAvailableCheckResponses = List.of(
                new MemberCouponAvailableCheckResponse(1L, DiscountType.FIXED, 1000, "네네치킨 시청점 할인 쿠폰", LocalDate.of(2024, 12, 31), 15000, false),
                new MemberCouponAvailableCheckResponse(2L, DiscountType.FIXED, 2000, "네네치킨 시청점 할인 쿠폰", LocalDate.of(2024, 12, 31), 20000, true),
                new MemberCouponAvailableCheckResponse(3L, DiscountType.PERCENT, 10, "치킨대전 이벤트 10% 할인 쿠폰", LocalDate.of(2024, 11, 15), 20000, true)
        );
        Mockito.when(memberCouponService.availableCheckMemberCoupons(memberId, storeId, orderId)).thenReturn(memberCouponAvailableCheckResponses);

        // When
        ResultActions result = mockMvc.perform(get("/api/v1/stores/{storeId}/orders/{orderId}/member-coupons/check", storeId, orderId)
                .accept(MediaType.APPLICATION_JSON));

        // Then
        result.andDo(document("{class-name}/{method-name}",
                        preprocessRequest(Preprocessors.prettyPrint()),
                        preprocessResponse(Preprocessors.prettyPrint()),
                        pathParameters(
                                parameterWithName("storeId").description("스토어 아이디"),
                                parameterWithName("orderId").description("주문 아이디")
                        ),
                        responseFields(
                                beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("멤버쿠폰 아이디"),
                                fieldWithPath("discountType").type(JsonFieldType.STRING).description("할인 타입"),
                                fieldWithPath("discountAmount").type(JsonFieldType.NUMBER).description("할인 금액"),
                                fieldWithPath("couponName").type(JsonFieldType.STRING).description("쿠폰 이름"),
                                fieldWithPath("expiredAt").type(JsonFieldType.STRING).description("유효 기간 종료 날짜"),
                                fieldWithPath("minOrderPrice").type(JsonFieldType.NUMBER).description("최소 주문 가격"),
                                fieldWithPath("available").type(JsonFieldType.BOOLEAN).description("사용 가능 여부")
                        )
                ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[0].discountType", is("FIXED")))
                .andExpect(jsonPath("$.data[0].discountAmount", is(1000)))
                .andExpect(jsonPath("$.data[0].couponName", is("네네치킨 시청점 할인 쿠폰")))
                .andExpect(jsonPath("$.data[0].expiredAt", is("2024-12-31")))
                .andExpect(jsonPath("$.data[0].minOrderPrice", is(15000)))
                .andExpect(jsonPath("$.data[0].available", is(false)))
                .andExpect(jsonPath("$.data[1].id", is(2)))
                .andExpect(jsonPath("$.data[1].discountType", is("FIXED")))
                .andExpect(jsonPath("$.data[1].discountAmount", is(2000)))
                .andExpect(jsonPath("$.data[1].couponName", is("네네치킨 시청점 할인 쿠폰")))
                .andExpect(jsonPath("$.data[1].expiredAt", is("2024-12-31")))
                .andExpect(jsonPath("$.data[1].minOrderPrice", is(20000)))
                .andExpect(jsonPath("$.data[1].available", is(true)))
                .andExpect(jsonPath("$.data[2].id", is(3)))
                .andExpect(jsonPath("$.data[2].discountType", is("PERCENT")))
                .andExpect(jsonPath("$.data[2].discountAmount", is(10)))
                .andExpect(jsonPath("$.data[2].couponName", is("치킨대전 이벤트 10% 할인 쿠폰")))
                .andExpect(jsonPath("$.data[2].expiredAt", is("2024-11-15")))
                .andExpect(jsonPath("$.data[2].minOrderPrice", is(20000)))
                .andExpect(jsonPath("$.data[2].available", is(true)));
    }
}
