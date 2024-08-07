package com.github.hji1235.coupon_system.test.controller;

import com.github.hji1235.config.AbstractRestDocsTests;
import com.github.hji1235.coupon_system.test.dto.TestRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApiTestController.class)
class ApiTestControllerTest extends AbstractRestDocsTests {

    @Test
    @DisplayName("Rest docs API 문서 테스트1")
    void test1() throws Exception {
        ResultActions result = mockMvc.perform(get("/api/v1/test1"));

        result.andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}")
                );
    }


    @Test
    void test2() throws Exception {
        int testId = 1;
        TestRequestDto testRequestDto = new TestRequestDto("CMH", 27);

        ResultActions result = mockMvc.perform(post("/api/v1/test2/{testId}", testId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(testRequestDto)));

        result.andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}",
                        pathParameters(
                                parameterWithName("testId").description("테스트용 ID")
                        ),
                        PayloadDocumentation.requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이")
                        ),
                        PayloadDocumentation.responseFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이")
                        )
                ));
    }

    @Test
    void test3() {
        ApiTestController apiTestController = new ApiTestController();
        Assertions.assertThat(apiTestController.test3()).isEqualTo("hello ccc"); //실패
    }
}