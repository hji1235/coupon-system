package com.github.hji1235.coupon_system.test.document;

import com.github.hji1235.config.AbstractRestDocsTests;
import com.github.hji1235.coupon_system.controller.MenuController;
import com.github.hji1235.coupon_system.controller.dto.store.MenuFindResponse;
import com.github.hji1235.coupon_system.controller.dto.store.MenuSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.store.MenuUpdateRequest;
import com.github.hji1235.coupon_system.service.MenuService;
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

@WebMvcTest(MenuController.class)
class MenuDocumentationTest extends AbstractRestDocsTests {

    @MockBean
    private MenuService menuService;

    @Test
    @DisplayName("메뉴 생성")
    void createMenu() throws Exception {
        // Given
        Long storeId = 1L;
        MenuSaveRequest menuSaveRequest = new MenuSaveRequest("후라이드 치킨", 15000);
        Mockito.when(menuService.saveMenu(ArgumentMatchers.eq(storeId), ArgumentMatchers.any(MenuSaveRequest.class))).thenReturn(1L);

        // When
        ResultActions result = mockMvc.perform(post("/api/v1/stores/{storeId}/menus", storeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(menuSaveRequest)));

        // Then
        result.andDo(document("{class-name}/{method-name}",
                        preprocessRequest(Preprocessors.prettyPrint()),
                        preprocessResponse(Preprocessors.prettyPrint()),
                        pathParameters(
                                parameterWithName("storeId").description("스토어 아이디")
                        ),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("메뉴 이름"),
                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("가격")
                        )
                ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", is(1)))
                .andExpect(jsonPath("$.error", is(nullValue())));
    }

    @Test
    @DisplayName("메뉴 단일 조회")
    void getMenu() throws Exception {
        // Given
        Long storeId = 1L;
        Long menuId = 1L;
        MenuFindResponse menuFindResponse = new MenuFindResponse(menuId, "후라이드 치킨", 15000);
        Mockito.when(menuService.findMenu(storeId, menuId)).thenReturn(menuFindResponse);

        // When
        ResultActions result = mockMvc.perform(get("/api/v1/stores/{storeId}/menus/{menuId}", storeId, menuId)
                .accept(MediaType.APPLICATION_JSON)
        );

        // Then
        result.andDo(document("{class-name}/{method-name}",
                        preprocessRequest(Preprocessors.prettyPrint()),
                        preprocessResponse(Preprocessors.prettyPrint()),
                        pathParameters(
                                parameterWithName("storeId").description("스토어 아이디"),
                                parameterWithName("menuId").description("메뉴 아이디")

                        ),
                        responseFields(
                                beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("메뉴 아이디"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("메뉴 이름"),
                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("가격")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.name", is("후라이드 치킨")))
                .andExpect(jsonPath("$.data.price", is(15000)))
                .andExpect(jsonPath("$.error", is(nullValue())));
    }

    @Test
    @DisplayName("메뉴 다중 조회")
    void getAllMenus() throws Exception {
        // Given
        Long storeId = 1L;
        List<MenuFindResponse> menuFindResponses = List.of(
                new MenuFindResponse(1L, "후라이드 치킨", 15000),
                new MenuFindResponse(2L, "양념 치킨", 17000),
                new MenuFindResponse(3L, "콜라", 2000)
        );
        Mockito.when(menuService.findAllMenus(storeId)).thenReturn(menuFindResponses);

        // When
        ResultActions result = mockMvc.perform(get("/api/v1/stores/{storeId}/menus", storeId)
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
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("메뉴 아이디"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("메뉴 이름"),
                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("가격")
                        )
                ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[0].name", is("후라이드 치킨")))
                .andExpect(jsonPath("$.data[0].price", is(15000)))
                .andExpect(jsonPath("$.data[1].id", is(2)))
                .andExpect(jsonPath("$.data[1].name", is("양념 치킨")))
                .andExpect(jsonPath("$.data[1].price", is(17000)))
                .andExpect(jsonPath("$.data[2].id", is(3)))
                .andExpect(jsonPath("$.data[2].name", is("콜라")))
                .andExpect(jsonPath("$.data[2].price", is(2000)));
    }

    @Test
    @DisplayName("메뉴 수정")
    void updateMenu() throws Exception {
        // Given
        Long storeId = 1L;
        Long menuId = 1L;
        MenuUpdateRequest menuUpdateRequest = new MenuUpdateRequest("핫후라이드 치킨", 16000);

        // When
        ResultActions result = mockMvc.perform(patch("/api/v1/stores/{storeId}/menus/{menuId}", storeId, menuId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(menuUpdateRequest))
                .accept(MediaType.APPLICATION_JSON));

        // Then
        result.andDo(document("{class-name}/{method-name}",
                        preprocessRequest(Preprocessors.prettyPrint()),
                        preprocessResponse(Preprocessors.prettyPrint()),
                        pathParameters(
                                parameterWithName("storeId").description("스토어 아이디"),
                                parameterWithName("menuId").description("메뉴 아이디")
                        ),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("변경할 이름"),
                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("변경할 가격")
                        )
                ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", is(nullValue())))
                .andExpect(jsonPath("$.error", is(nullValue())));
    }
}
