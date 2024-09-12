package com.github.hji1235.coupon_system.test.document;

import com.github.hji1235.config.AbstractRestDocsTests;
import com.github.hji1235.coupon_system.controller.BrandController;
import com.github.hji1235.coupon_system.controller.dto.store.BrandFindResponse;
import com.github.hji1235.coupon_system.controller.dto.store.BrandSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.store.BrandUpdateRequest;
import com.github.hji1235.coupon_system.service.BrandService;
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

@WebMvcTest(BrandController.class)
class BrandDocumentationTest extends AbstractRestDocsTests {

    @MockBean
    private BrandService brandService;

    @Test
    @DisplayName("브랜드 생성")
    void createBrand() throws Exception {
        // Given
        BrandSaveRequest brandSaveRequest = new BrandSaveRequest("네네치킨");
        Mockito.when(brandService.saveBrand(ArgumentMatchers.any(BrandSaveRequest.class))).thenReturn(1L);

        // When
        ResultActions result = mockMvc.perform(post("/api/v1/brands")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(brandSaveRequest)));

        // Then
        result.andDo(document("{class-name}/{method-name}",
                preprocessRequest(Preprocessors.prettyPrint()),
                preprocessResponse(Preprocessors.prettyPrint()),
                requestFields(
                        fieldWithPath("name").type(JsonFieldType.STRING).description("브랜드 이름")
                )
        ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", is(1)))
                .andExpect(jsonPath("$.error", is(nullValue())));
    }

    @Test
    @DisplayName("브랜드 단일 조회")
    void getBrand() throws Exception {
        // Given
        Long brandId = 1L;
        BrandFindResponse brandFindResponse = new BrandFindResponse(1L, "네네치킨");
        Mockito.when(brandService.findBrand(brandId)).thenReturn(brandFindResponse);

        // When
        ResultActions result = mockMvc.perform(get("/api/v1/brands/{brandId}", brandId)
                .accept(MediaType.APPLICATION_JSON));

        // Then
        result.andDo(document("{class-name}/{method-name}",
                preprocessRequest(Preprocessors.prettyPrint()),
                preprocessResponse(Preprocessors.prettyPrint()),
                pathParameters(
                        parameterWithName("brandId").description("브랜드 아이디")
                ),
                responseFields(
                        beneathPath("data").withSubsectionId("data"),
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("브랜드 아이디"),
                        fieldWithPath("name").type(JsonFieldType.STRING).description("브랜드 이름")
                )
        ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.name", is("네네치킨")))
                .andExpect(jsonPath("$.error", is(nullValue())));
    }

    @Test
    @DisplayName("브랜드 수정")
    void updateBrand() throws Exception {
        // Given
        Long brandId = 1L;
        BrandUpdateRequest brandUpdateRequest = new BrandUpdateRequest("눼눼치킨");

        // When
        ResultActions result = mockMvc.perform(patch("/api/v1/brands/{brandId}", brandId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(brandUpdateRequest))
                .accept(MediaType.APPLICATION_JSON));

        // Then
        result.andDo(document("{class-name}/{method-name}",
                preprocessRequest(Preprocessors.prettyPrint()),
                preprocessResponse(Preprocessors.prettyPrint()),
                pathParameters(
                        parameterWithName("brandId").description("브랜드 아이디")
                ),
                requestFields(
                        fieldWithPath("name").type(JsonFieldType.STRING).description("변경할 이름")
                )
        ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", is(nullValue())))
                .andExpect(jsonPath("$.error", is(nullValue())));
    }
}
