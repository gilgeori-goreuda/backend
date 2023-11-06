package com.pd.gilgeorigoreuda.store.controller;

import com.pd.gilgeorigoreuda.settings.ControllerTest;
import com.pd.gilgeorigoreuda.store.dto.request.FoodCategoryRequest;
import com.pd.gilgeorigoreuda.store.dto.request.StoreCreateRequest;
import com.pd.gilgeorigoreuda.store.dto.request.StoreUpdateRequest;
import com.pd.gilgeorigoreuda.store.dto.response.StoreCreateResponse;
import com.pd.gilgeorigoreuda.store.service.StoreService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static com.pd.gilgeorigoreuda.settings.restdocs.RestDocsConfiguration.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@AutoConfigureRestDocs
class StoreControllerTest extends ControllerTest {

//    private static final MemberTokens MEMBER_TOKENS = new MemberTokens("refreshToken", "accessToken");

    @MockBean
    private StoreService storeService;

    private void makeStore() throws Exception {
        final StoreCreateRequest storeCreateRequest = new StoreCreateRequest(
                "붕어빵 가게",
                "포장마차",
                LocalTime.of(10, 0),
                LocalTime.of(20, 0),
                "현금",
                "https://www.image.com",
                "월,수,금,토,일",
                new BigDecimal("37.123456"),
                new BigDecimal("127.123456"),
                "경기도 용인시 수지구 죽전동 123-456",
                new FoodCategoryRequest(
                        List.of("붕어빵", "떡뽁이", "어묵")
                )
        );

        when(storeService.saveStore(anyLong(), any(StoreCreateRequest.class)))
            .thenReturn(StoreCreateResponse.of(1L));


        performPostRequest(storeCreateRequest);
    }

    private ResultActions performGetRequest(final Long storeId) throws Exception {
        return mockMvc.perform(
                get("/api/v1/stores/{storeId}", storeId)
//                .header(AUTHORIZATION, MEMBER_TOKENS.getAccessToken())
                .contentType(APPLICATION_JSON)
        );
    }

    private ResultActions performPostRequest(final StoreCreateRequest storeCreateRequest) throws Exception {
        return mockMvc.perform(
                post("/api/v1/stores")
//                .header(AUTHORIZATION, MEMBER_TOKENS.getAccessToken())
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(storeCreateRequest))
        );
    }

    private ResultActions performPutRequest(final StoreUpdateRequest storeUpdateRequest) throws Exception {
        return mockMvc.perform(
                put("/api/v1/stores")
//                .header(AUTHORIZATION, MEMBER_TOKENS.getAccessToken())
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(storeUpdateRequest))
        );
    }

    private ResultActions performDeleteRequest(final Long storeId) throws Exception {
        return mockMvc.perform(
                delete("/api/v1/stores/{storeId}", storeId)
//                .header(AUTHORIZATION, MEMBER_TOKENS.getAccessToken())
                .contentType(APPLICATION_JSON)
        );
    }

    @Test
    @DisplayName("회원은 가게를 제보할 수 있다.")
    void createStore() throws Exception {
        // given
        final StoreCreateRequest storeCreateRequest = new StoreCreateRequest(
                "붕어빵 가게",
                "포장마차",
                LocalTime.of(10, 0),
                LocalTime.of(20, 0),
                "현금",
                "https://www.image.com",
                "월,수,금,토,일",
                new BigDecimal("37.123456"),
                new BigDecimal("127.123456"),
                "경기도 용인시 수지구 죽전동 123-456",
                new FoodCategoryRequest(
                        List.of("붕어빵", "떡뽁이", "어묵")
                )
        );

        when(storeService.saveStore(anyLong(), any(StoreCreateRequest.class)))
                .thenReturn(StoreCreateResponse.of(1L));

        // when
        ResultActions resultActions = performPostRequest(storeCreateRequest);

        // then
        resultActions.andExpect(status().isCreated())
                .andExpect(header().string(LOCATION, "/api/v1/stores/1"))
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("name")
                                                .type(JsonFieldType.STRING)
                                                .description("가게 이름")
                                                .attributes(field("constraint", "최소 1자 최대 50자")),
                                        fieldWithPath("storeType")
                                                .type(JsonFieldType.STRING)
                                                .description("가게 타입 (EnumType: FOOD_TRUCK, FOOD_STALL)")
                                                .attributes(field("constraint", "푸드트럭, 포장마차 중 하나 선택")),
                                        fieldWithPath("openTime")
                                                .type(JsonFieldType.STRING)
                                                .description("오픈 시간")
                                                .attributes(field("constraint", "HH:mm")),
                                        fieldWithPath("closeTime")
                                                .type(JsonFieldType.STRING)
                                                .description("마감 시간")
                                                .attributes(field("constraint", "HH:mm")),
                                        fieldWithPath("purchaseType")
                                                .type(JsonFieldType.STRING)
                                                .description("결제 방식 (EnumType: CARD, CASH, ACCOUNT_TRANSFER)")
                                                .attributes(field("constraint", "현금, 카드, 계좌이체 중 복수 선택가능")),
                                        fieldWithPath("imageUrl")
                                                .type(JsonFieldType.STRING)
                                                .description("이미지 URL")
                                                .optional()
                                                .attributes(field("constraint", "유효한 URL 형식")),
                                        fieldWithPath("businessDates")
                                                .type(JsonFieldType.STRING)
                                                .description("영업 요일")
                                                .attributes(field("constraint", "월,화,수,목,금,토,일 중 복수 선택가능")),
                                        fieldWithPath("lat")
                                                .type(JsonFieldType.NUMBER)
                                                .description("위도")
                                                .attributes(field("constraint", "BigDecimal(3, 13)")),
                                        fieldWithPath("lng")
                                                .type(JsonFieldType.NUMBER)
                                                .description("경도")
                                                .attributes(field("constraint", "BigDecimal(3, 13)")),
                                        fieldWithPath("streetAddress")
                                                .type(JsonFieldType.STRING)
                                                .description("도로명 주소")
                                                .attributes(field("constraint", "필수 값")),
                                        fieldWithPath("foodCategories.foodCategories")
                                                .type(JsonFieldType.ARRAY)
                                                .description("음식 카테고리 배열")
                                                .attributes(field("constraint", "문자열 배열"))
                                ),
                                responseHeaders(
                                        headerWithName(LOCATION).description("가게 생성 URI")
                                )
                        )
                );

    }


}