package com.github.hji1235.coupon_system.test.document;

import com.github.hji1235.config.AbstractRestDocsTests;
import com.github.hji1235.coupon_system.controller.PaymentController;
import com.github.hji1235.coupon_system.controller.dto.payment.PaymentSaveRequest;
import com.github.hji1235.coupon_system.domain.order.PaymentMethod;
import com.github.hji1235.coupon_system.service.PaymentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

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

@WebMvcTest(PaymentController.class)
class PaymentDocumentationTest extends AbstractRestDocsTests {

    @MockBean
    private PaymentService paymentService;

    @Test
    @DisplayName("결제 생성")
    void createPayment() throws Exception {
        // Given
        Long orderId = 1L;
        PaymentSaveRequest paymentSaveRequest = new PaymentSaveRequest(PaymentMethod.TOSS_PAY, 1L);

        // When
        ResultActions result = mockMvc.perform(post("/api/v1/orders/{orderId}/payments", orderId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paymentSaveRequest)));

        // Then
        result.andDo(document("{class-name}/{method-name}",
                        preprocessRequest(Preprocessors.prettyPrint()),
                        preprocessResponse(Preprocessors.prettyPrint()),
                        pathParameters(
                                parameterWithName("orderId").description("주문 아이디")
                        ),
                        requestFields(
                                fieldWithPath("paymentMethod").type(JsonFieldType.STRING).description("결제 수단"),
                                fieldWithPath("memberCouponId").type(JsonFieldType.NUMBER).description("멤버쿠폰 아이디")
                        )
                ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", is(nullValue())))
                .andExpect(jsonPath("$.error", is(nullValue())));
    }

    @Test
    @DisplayName("결제 완료")
    void completePayment() throws Exception {
        // Given
        Long paymentId = 1L;

        // When
        ResultActions result = mockMvc.perform(patch("/api/v1/payments/{paymentId}", paymentId)
                .accept(MediaType.APPLICATION_JSON));

        // Then
        result.andDo(document("{class-name}/{method-name}",
                        preprocessRequest(Preprocessors.prettyPrint()),
                        preprocessResponse(Preprocessors.prettyPrint()),
                        pathParameters(
                                parameterWithName("paymentId").description("결제 아이디")
                        )
                ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", is(nullValue())))
                .andExpect(jsonPath("$.error", is(nullValue())));
    }
}
