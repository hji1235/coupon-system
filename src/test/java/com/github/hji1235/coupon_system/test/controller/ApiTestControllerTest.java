package com.github.hji1235.coupon_system.test.controller;

import com.github.hji1235.config.AbstractRestDocsTests;
import com.github.hji1235.coupon_system.controller.ApiTestController;
import com.github.hji1235.coupon_system.controller.dto.TestRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
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

        result.andDo(document("{class-name}/{method-name}",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        pathParameters(
                                parameterWithName("testId").description("테스트용 ID")
                        ),
                        PayloadDocumentation.requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이")
                        ),
                        PayloadDocumentation.responseFields(
                                beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이")
                        )
                ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(ApiTestController.class))
                .andExpect(handler().methodName("test2"))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.name", is("CMH")))
                .andExpect(jsonPath("$.data.age", is(27)))
                .andExpect(jsonPath("$.error", is(nullValue())));
    }

    @Test
    void test3() {
        ApiTestController apiTestController = new ApiTestController();
        Assertions.assertThat(apiTestController.test3()).isEqualTo("hello 성공!!!!!"); //성공
    }
}