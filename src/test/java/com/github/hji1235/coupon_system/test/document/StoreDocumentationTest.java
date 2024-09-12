package com.github.hji1235.coupon_system.test.document;

import com.github.hji1235.config.AbstractRestDocsTests;
import com.github.hji1235.coupon_system.controller.StoreController;
import com.github.hji1235.coupon_system.controller.dto.store.StoreFindResponse;
import com.github.hji1235.coupon_system.controller.dto.store.StoreSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.store.StoreUpdateRequest;
import com.github.hji1235.coupon_system.service.StoreService;
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

@WebMvcTest(StoreController.class)
class StoreDocumentationTest extends AbstractRestDocsTests {

    @MockBean
    private StoreService storeService;

    @Test
    @DisplayName("스토어 생성")
    void createStore() throws Exception {
        // Given
        Long brandId = 1L;
        StoreSaveRequest storeSaveRequest = new StoreSaveRequest("네네치킨-시청점", brandId);
        Mockito.when(storeService.saveStore(ArgumentMatchers.any(StoreSaveRequest.class))).thenReturn(1L);

        // When
        ResultActions result = mockMvc.perform(post("/api/v1/stores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(storeSaveRequest))
        );

        // Then
        result.andDo(document("{class-name}/{method-name}",
                preprocessRequest(Preprocessors.prettyPrint()),
                preprocessResponse(Preprocessors.prettyPrint()),
                requestFields(
                        fieldWithPath("name").type(JsonFieldType.STRING).description("스토어 이름"),
                        fieldWithPath("brandId").type(JsonFieldType.NUMBER).description("브랜드 아이디")
                )
        ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", is(1)))
                .andExpect(jsonPath("$.error", is(nullValue())));
    }

    @Test
    @DisplayName("스토어 단일 조회")
    void getStore() throws Exception {
        // Given
        Long storeId = 1L;
        StoreFindResponse storeFindResponse = new StoreFindResponse(storeId, "네네치킨-시청점");
        Mockito.when(storeService.findStore(storeId)).thenReturn(storeFindResponse);

        // When
        ResultActions result = mockMvc.perform(get("/api/v1/stores/{storeId}", storeId)
                .accept(MediaType.APPLICATION_JSON)
        );

        // Then
        result.andDo(document("{class-name}/{method-name}",
                preprocessRequest(Preprocessors.prettyPrint()),
                preprocessResponse(Preprocessors.prettyPrint()),
                pathParameters(
                        parameterWithName("storeId").description("스토어 아이디")
                ),
                responseFields(
                        beneathPath("data").withSubsectionId("data"),
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("스토어 아이디"),
                        fieldWithPath("name").type(JsonFieldType.STRING).description("스토어 이름")
                )
        ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.name", is("네네치킨-시청점")))
                .andExpect(jsonPath("$.error", is(nullValue())));
    }

    @Test
    @DisplayName("스토어 다중 조회")
    void getAllStores() throws Exception {
        // Given
        List<StoreFindResponse> storeFindResponses = List.of(
                new StoreFindResponse(1L, "네네치킨-시청점"),
                new StoreFindResponse(2L, "네네치킨-서면점"),
                new StoreFindResponse(3L, "네네치킨-부전점")
        );
        Mockito.when(storeService.findAllStores()).thenReturn(storeFindResponses);

        // When
        ResultActions result = mockMvc.perform(get("/api/v1/stores")
                .accept(MediaType.APPLICATION_JSON)
        );

        // Then
        result.andDo(document("{class-name}/{method-name}",
                preprocessRequest(Preprocessors.prettyPrint()),
                preprocessResponse(Preprocessors.prettyPrint()),
                responseFields(
                        beneathPath("data").withSubsectionId("data"),
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("스토어 아이디"),
                        fieldWithPath("name").type(JsonFieldType.STRING).description("스토어 이름")
                )
        ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[0].name", is("네네치킨-시청점")))
                .andExpect(jsonPath("$.data[1].id", is(2)))
                .andExpect(jsonPath("$.data[1].name", is("네네치킨-서면점")))
                .andExpect(jsonPath("$.data[2].id", is(3)))
                .andExpect(jsonPath("$.data[2].name", is("네네치킨-부전점")));
    }

    @Test
    @DisplayName("스토어 수정")
    void updateStore() throws Exception {
        // Given
        Long storeId = 1L;
        StoreUpdateRequest storeUpdateRequest = new StoreUpdateRequest("네네치킨-시청역점");

        // When
        ResultActions result = mockMvc.perform(get("/api/v1/stores/{storeId}", storeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(storeUpdateRequest))
                .accept(MediaType.APPLICATION_JSON)
        );

        // Then
        result.andDo(document("{class-name}/{method-name}",
                        preprocessRequest(Preprocessors.prettyPrint()),
                        preprocessResponse(Preprocessors.prettyPrint()),
                        pathParameters(
                                parameterWithName("storeId").description("스토어 아이디")
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
