package com.pd.gilgeorigoreuda.store.controller;

import com.pd.gilgeorigoreuda.login.domain.MemberAccessRefreshToken;
import com.pd.gilgeorigoreuda.login.domain.MemberToken;
import com.pd.gilgeorigoreuda.settings.ControllerTest;
import com.pd.gilgeorigoreuda.store.dto.request.FoodCategoryRequest;
import com.pd.gilgeorigoreuda.store.dto.request.StoreCreateRequest;
import com.pd.gilgeorigoreuda.store.dto.request.StoreUpdateRequest;
import com.pd.gilgeorigoreuda.store.dto.response.StoreCreateResponse;
import com.pd.gilgeorigoreuda.store.dto.response.StoreOwnerResponse;
import com.pd.gilgeorigoreuda.store.dto.response.StoreResponse;
import com.pd.gilgeorigoreuda.store.service.StoreService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static com.pd.gilgeorigoreuda.settings.restdocs.RestDocsConfiguration.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@WebMvcTest(StoreController.class)
@AutoConfigureRestDocs
class StoreControllerTest extends ControllerTest {

    private static final MemberAccessRefreshToken MEMBER_ACCESS_REFRESH_TOKEN = MemberAccessRefreshToken.of("refreshToken", "accessToken");
    private static final MemberToken MEMBER_TOKEN = MemberToken.of(1L, "accessToken" ,"refreshToken");

    @MockBean
    private StoreService storeService;

    @BeforeEach
    void setUp() {
        given(memberTokenRepository.findByAccessToken(any())).willReturn(Optional.of(MEMBER_TOKEN));
        doNothing().when(jwtProvider).validateTokens(any());
        given(jwtProvider.getSubject(any())).willReturn("1");
    }

    private void makeStore() throws Exception {
        StoreCreateRequest storeCreateRequest = new StoreCreateRequest(
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

    private ResultActions performGetRequest(final Long storeId, final String lat, final String lng) throws Exception {
        return mockMvc.perform(
                get("/api/v1/stores/{storeId}", storeId)
                        .param("lat", lat)
                        .param("lng", lng)
                .header(AUTHORIZATION, MEMBER_ACCESS_REFRESH_TOKEN.getAccessToken())
                        .contentType(APPLICATION_JSON)
        );
    }

    private ResultActions performPostRequest(final StoreCreateRequest storeCreateRequest) throws Exception {
        return mockMvc.perform(
                post("/api/v1/stores")
                .header(AUTHORIZATION, MEMBER_ACCESS_REFRESH_TOKEN.getAccessToken())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(storeCreateRequest))
        );
    }

    private ResultActions performPutRequest(final StoreUpdateRequest storeUpdateRequest, final Long storeId) throws Exception {
        return mockMvc.perform(
                put("/api/v1/stores/{storeId}", storeId)
                .header(AUTHORIZATION, MEMBER_ACCESS_REFRESH_TOKEN.getAccessToken())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(storeUpdateRequest))
        );
    }

    private ResultActions performDeleteRequest(final Long storeId) throws Exception {
        return mockMvc.perform(
                delete("/api/v1/stores/{storeId}", storeId)
                .header(AUTHORIZATION, MEMBER_ACCESS_REFRESH_TOKEN.getAccessToken())
                        .contentType(APPLICATION_JSON)
        );
    }

    @Test
    @DisplayName("회원은 가게를 제보할 수 있다.")
    void createStore() throws Exception {
        // given
        StoreCreateRequest storeCreateRequest = new StoreCreateRequest(
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
        resultActions
                .andExpect(status().isCreated())
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

        then(storeService).should(times(1)).saveStore(anyLong(), any(StoreCreateRequest.class));
    }

    @Nested
    @DisplayName("가게 생성 요청 검증")
    class storeCreateRequestValidationTest {

        @Test
        @DisplayName("가게 이름이 null일 경우 예외가 발생한다.")
        void whenStoreNameIsNullShouldThrowException() throws Exception {
            // given
            StoreCreateRequest badStoreCreateRequest = new StoreCreateRequest(
                    null,
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

            // when
            ResultActions resultActions = performPostRequest(badStoreCreateRequest);

            // then
            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("name : 가게 이름을 입력해주세요. (request value: null)"));

            then(storeService).should(never()).saveStore(anyLong(), any(StoreCreateRequest.class));
        }

        @Test
        @DisplayName("가게 이름이 50자 초과일 경우 예외가 발생한다.")
        void whenStoreNameIsOver50CharactersShouldThrowException() throws Exception {
            // given
            StoreCreateRequest badStoreCreateRequest = new StoreCreateRequest(
                    "1234" + "1".repeat(50),
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

            // when
            ResultActions resultActions = performPostRequest(badStoreCreateRequest);

            // then
            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("name : 가게 이름은 1에서 50자 사이여야 합니다. (request value: 123411111111111111111111111111111111111111111111111111)"));

            then(storeService).should(never()).saveStore(anyLong(), any(StoreCreateRequest.class));
        }

        @Test
        @DisplayName("가게 타입이 null일 경우 예외가 발생한다.")
        void whenStoreTypeIsNullShouldThrowException() throws Exception {
            // given
            StoreCreateRequest badStoreCreateRequest = new StoreCreateRequest(
                    "붕어빵 가게",
                    null,
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

            // when
            ResultActions resultActions = performPostRequest(badStoreCreateRequest);

            // then
            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("storeType : 가게 타입을 선택해주세요. (request value: null)"));

            then(storeService).should(never()).saveStore(anyLong(), any(StoreCreateRequest.class));
        }

        @Test
        @DisplayName("가게 타입이 blank일 경우 예외가 발생한다.")
        void whenStoreTypeIsBlankShouldThrowException() throws Exception {
            // given
            StoreCreateRequest badStoreCreateRequest = new StoreCreateRequest(
                    "붕어빵 가게",
                    "",
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

            // when
            ResultActions resultActions = performPostRequest(badStoreCreateRequest);

            // then
            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("storeType : 가게 타입을 선택해주세요. (request value: )"));

            then(storeService).should(never()).saveStore(anyLong(), any(StoreCreateRequest.class));
        }

        @Test
        @DisplayName("결제 방식이 null일 경우 예외가 발생한다.")
        void whenPurchaseTypeIsNullShouldThrowException() throws Exception {
            // given
            StoreCreateRequest badStoreCreateRequest = new StoreCreateRequest(
                    "붕어빵 가게",
                    "포장마차",
                    LocalTime.of(10, 0),
                    LocalTime.of(20, 0),
                    null,
                    "https://www.image.com",
                    "월,수,금,토,일",
                    new BigDecimal("37.123456"),
                    new BigDecimal("127.123456"),
                    "경기도 용인시 수지구 죽전동 123-456",
                    new FoodCategoryRequest(
                            List.of("붕어빵", "떡뽁이", "어묵")
                    )
            );

            // when
            ResultActions resultActions = performPostRequest(badStoreCreateRequest);

            // then
            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("purchaseType : 결제방식을 선택해주세요. (request value: null)"));

            then(storeService).should(never()).saveStore(anyLong(), any(StoreCreateRequest.class));
        }

        @Test
        @DisplayName("결제 방식이 blank일 경우 예외가 발생한다.")
        void whenPurchaseTypeIsBlankShouldThrowException() throws Exception {
            // given
            StoreCreateRequest badStoreCreateRequest = new StoreCreateRequest(
                    "붕어빵 가게",
                    "포장마차",
                    LocalTime.of(10, 0),
                    LocalTime.of(20, 0),
                    "",
                    "https://www.image.com",
                    "월,수,금,토,일",
                    new BigDecimal("37.123456"),
                    new BigDecimal("127.123456"),
                    "경기도 용인시 수지구 죽전동 123-456",
                    new FoodCategoryRequest(
                            List.of("붕어빵", "떡뽁이", "어묵")
                    )
            );

            // when
            ResultActions resultActions = performPostRequest(badStoreCreateRequest);

            // then
            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("purchaseType : 결제방식을 선택해주세요. (request value: )"));

            then(storeService).should(never()).saveStore(anyLong(), any(StoreCreateRequest.class));
        }

        @Test
        @DisplayName("가게 이미지 URL이 유효하지 않을 경우 예외가 발생한다.")
        void whenImageUrlIsInvalidShouldThrowException() throws Exception {
            // given
            StoreCreateRequest badStoreCreateRequest = new StoreCreateRequest(
                    "붕어빵 가게",
                    "포장마차",
                    LocalTime.of(10, 0),
                    LocalTime.of(20, 0),
                    "현금",
                    "image.com",
                    "월,수,금,토,일",
                    new BigDecimal("37.123456"),
                    new BigDecimal("127.123456"),
                    "경기도 용인시 수지구 죽전동 123-456",
                    new FoodCategoryRequest(
                            List.of("붕어빵", "떡뽁이", "어묵")
                    )
            );

            // when
            ResultActions resultActions = performPostRequest(badStoreCreateRequest);

            // then
            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("imageUrl : 유효한 URL을 입력해주세요. (request value: image.com)"));

            then(storeService).should(never()).saveStore(anyLong(), any(StoreCreateRequest.class));
        }

        @Test
        @DisplayName("가게 위치 위도가 null일 경우 예외가 발생한다.")
        void whenLatIsNullShouldThrowException() throws Exception {
            // given
            StoreCreateRequest badStoreCreateRequest = new StoreCreateRequest(
                    "붕어빵 가게",
                    "포장마차",
                    LocalTime.of(10, 0),
                    LocalTime.of(20, 0),
                    "현금",
                    "https://www.image.com",
                    "월,수,금,토,일",
                    null,
                    new BigDecimal("127.123456"),
                    "경기도 용인시 수지구 죽전동 123-456",
                    new FoodCategoryRequest(
                            List.of("붕어빵", "떡뽁이", "어묵")
                    )
            );

            // when
            ResultActions resultActions = performPostRequest(badStoreCreateRequest);

            // then
            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("lat : 위도를 입력해주세요. (request value: null)"));

            then(storeService).should(never()).saveStore(anyLong(), any(StoreCreateRequest.class));
        }

        @Test
        @DisplayName("가게 위치 경도가 null일 경우 예외가 발생한다.")
        void whenLngIsNullShouldThrowException() throws Exception {
            // given
            StoreCreateRequest badStoreCreateRequest = new StoreCreateRequest(
                    "붕어빵 가게",
                    "포장마차",
                    LocalTime.of(10, 0),
                    LocalTime.of(20, 0),
                    "현금",
                    "https://www.image.com",
                    "월,수,금,토,일",
                    new BigDecimal("37.123456"),
                    null,
                    "경기도 용인시 수지구 죽전동 123-456",
                    new FoodCategoryRequest(
                            List.of("붕어빵", "떡뽁이", "어묵")
                    )
            );

            // when
            ResultActions resultActions = performPostRequest(badStoreCreateRequest);

            // then
            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("lng : 경도를 입력해주세요. (request value: null)"));

            then(storeService).should(never()).saveStore(anyLong(), any(StoreCreateRequest.class));
        }

        @Test
        @DisplayName("가게 위치 위도가 유효 범위에서 벗어난 경우 예외가 발생한다.")
        void whenLatIsOutOfBoundShouldThrowException() throws Exception {
            // given
            StoreCreateRequest badStoreCreateRequest = new StoreCreateRequest(
                    "붕어빵 가게",
                    "포장마차",
                    LocalTime.of(10, 0),
                    LocalTime.of(20, 0),
                    "현금",
                    "https://www.image.com",
                    "월,수,금,토,일",
                    new BigDecimal("123121.123456"),
                    new BigDecimal("127.123456"),
                    "경기도 용인시 수지구 죽전동 123-456",
                    new FoodCategoryRequest(
                            List.of("붕어빵", "떡뽁이", "어묵")
                    )
            );

            // when
            ResultActions resultActions = performPostRequest(badStoreCreateRequest);

            // then
            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errorCode").value("Digits"))
                    .andExpect(jsonPath("$.message").value("lat : numeric value out of bounds (<3 digits>.<38 digits> expected) (request value: 123121.123456)"));

            then(storeService).should(never()).saveStore(anyLong(), any(StoreCreateRequest.class));
        }

        @Test
        @DisplayName("가게 위치 위도가 음수인 경우 예외가 발생한다.")
        void whenLatIsNegativeShouldThrowException() throws Exception {
            // given
            StoreCreateRequest badStoreCreateRequest = new StoreCreateRequest(
                    "붕어빵 가게",
                    "포장마차",
                    LocalTime.of(10, 0),
                    LocalTime.of(20, 0),
                    "현금",
                    "https://www.image.com",
                    "월,수,금,토,일",
                    new BigDecimal("-37.123456"),
                    new BigDecimal("127.123456"),
                    "경기도 용인시 수지구 죽전동 123-456",
                    new FoodCategoryRequest(
                            List.of("붕어빵", "떡뽁이", "어묵")
                    )
            );

            // when
            ResultActions resultActions = performPostRequest(badStoreCreateRequest);

            // then
            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errorCode").value("Positive"))
                    .andExpect(jsonPath("$.message").value("lat : 음수 값은 허용되지 않습니다. (request value: -37.123456)"));

            then(storeService).should(never()).saveStore(anyLong(), any(StoreCreateRequest.class));
        }

        @Test
        @DisplayName("가게 위치 경도가 범위를 벗어난 경우 예외가 발생한다.")
        void whenLngIsOutOfBoundShouldThrowException() throws Exception {
            // given
            StoreCreateRequest badStoreCreateRequest = new StoreCreateRequest(
                    "붕어빵 가게",
                    "포장마차",
                    LocalTime.of(10, 0),
                    LocalTime.of(20, 0),
                    "현금",
                    "https://www.image.com",
                    "월,수,금,토,일",
                    new BigDecimal("37.123456"),
                    new BigDecimal("123121.123456"),
                    "경기도 용인시 수지구 죽전동 123-456",
                    new FoodCategoryRequest(
                            List.of("붕어빵", "떡뽁이", "어묵")
                    )

            );

            // when
            ResultActions resultActions = performPostRequest(badStoreCreateRequest);

            // then
            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errorCode").value("Digits"))
                    .andExpect(jsonPath("$.message").value("lng : numeric value out of bounds (<3 digits>.<38 digits> expected) (request value: 123121.123456)"));

            then(storeService).should(never()).saveStore(anyLong(), any(StoreCreateRequest.class));
        }

        @Test
        @DisplayName("가게 위치 경도가 음수인 경우 예외가 발생한다.")
        void whenLngIsNegativeShouldThrowException() throws Exception {
            // given
            StoreCreateRequest badStoreCreateRequest = new StoreCreateRequest(
                    "붕어빵 가게",
                    "포장마차",
                    LocalTime.of(10, 0),
                    LocalTime.of(20, 0),
                    "현금",
                    "https://www.image.com",
                    "월,수,금,토,일",
                    new BigDecimal("37.123456"),
                    new BigDecimal("-127.123456"),
                    "경기도 용인시 수지구 죽전동 123-456",
                    new FoodCategoryRequest(
                            List.of("붕어빵", "떡뽁이", "어묵")
                    )
            );

            // when
            ResultActions resultActions = performPostRequest(badStoreCreateRequest);

            // then
            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errorCode").value("Positive"))
                    .andExpect(jsonPath("$.message").value("lng : 음수 값은 허용되지 않습니다. (request value: -127.123456)"));

            then(storeService).should(never()).saveStore(anyLong(), any(StoreCreateRequest.class));
        }

        @Test
        @DisplayName("가게 위치 도로명 주소가 null일 경우 예외가 발생한다.")
        void whenStreetAddressIsNullShouldThrowException() throws Exception {
            // given
            StoreCreateRequest badStoreCreateRequest = new StoreCreateRequest(
                    "붕어빵",
                    "포장마차",
                    LocalTime.of(10, 0),
                    LocalTime.of(20, 0),
                    "현금",
                    "https://www.image.com",
                    "월,수,금,토,일",
                    new BigDecimal("37.123456"),
                    new BigDecimal("127.123456"),
                    null,
                    new FoodCategoryRequest(
                            List.of("붕어빵", "떡뽁이", "어묵")
                    )
            );

            // when
            ResultActions resultActions = performPostRequest(badStoreCreateRequest);

            // then
            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errorCode").value("NotBlank"))
                    .andExpect(jsonPath("$.message").value("streetAddress : 도로명 주소를 입력해주세요. (request value: null)"));

            then(storeService).should(never()).saveStore(anyLong(), any(StoreCreateRequest.class));
        }

        @Test
        @DisplayName("가게 음식 카테고리가 empty인 경우 예외가 발생한다.")
        void whenFoodCategoriesIsEmptyShouldThrowException() throws Exception {
            // given
            StoreCreateRequest badStoreCreateRequest = new StoreCreateRequest(
                    "붕어빵",
                    "포장마차",
                    LocalTime.of(10, 0),
                    LocalTime.of(20, 0),
                    "현금",
                    "https://www.image.com",
                    "월,수,금,토,일",
                    new BigDecimal("37.123456"),
                    new BigDecimal("127.123456"),
                    "경기도 용인시 수지구 죽전동 123-456",
                    new FoodCategoryRequest(List.of())
            );

            // when
            ResultActions resultActions = performPostRequest(badStoreCreateRequest);

            // then
            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errorCode").value("NotEmpty"))
                    .andExpect(jsonPath("$.message").value("foodCategories.foodCategories : 장소의 카테고리를 1개 이상 선택해주세요. (request value: [])"));

            then(storeService).should(never()).saveStore(anyLong(), any(StoreCreateRequest.class));
        }
    }

    @Test
    @DisplayName("회원은 가게 정보를 조회 수 있다.")
    void getStore() throws Exception {
        // given
        makeStore();

        StoreResponse storeResponse = new StoreResponse(
                1L,
                "붕어빵 가게",
                "포장마차",
                "수원역 5번출구 100m",
                4.5,
                "월,수,금",
                LocalTime.of(10, 0),
                LocalTime.of(20, 0),
                "현금",
                "https://www.image.com",
                new BigDecimal("37.123456"),
                new BigDecimal("127.123456"),
                "경기도 용인시 수지구 죽전동 123-456",
                10,
                LocalDateTime.of(2021, 8, 1, 0, 0, 0),
                100,
                List.of("붕어빵", "떡뽁이", "어묵"),
                "testNickname",
                new StoreOwnerResponse(
                        1L,
                        "testNickname"
                )
        );

        given(storeService.getStore(anyLong(), any(BigDecimal.class), any(BigDecimal.class))).willReturn(storeResponse);

        // when
        ResultActions resultActions = performGetRequest(1L, "37.123456", "127.123456");

        // then
        MvcResult mvcResult = resultActions
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("storeId").description("가게 ID")
                                ),
                                queryParameters(
                                        parameterWithName("lat").description("위도"),
                                        parameterWithName("lng").description("경도")
                                ),
                                responseFields(
                                        fieldWithPath("id")
                                                .type(JsonFieldType.NUMBER)
                                                .description("가게 ID"),
                                        fieldWithPath("name")
                                                .type(JsonFieldType.STRING)
                                                .description("가게 이름"),
                                        fieldWithPath("storeType")
                                                .type(JsonFieldType.STRING)
                                                .description("가게 타입 (EnumType: FOOD_TRUCK, FOOD_STALL)"),
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
                                                .description("결제 방식 (EnumType: CARD, CASH, ACCOUNT_TRANSFER)"),
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
                                        fieldWithPath("distanceFromMember")
                                                .type(JsonFieldType.NUMBER)
                                                .description("회원과 가게 사이의 거리 (단위: m)"),
                                        fieldWithPath("streetAddress")
                                                .type(JsonFieldType.STRING)
                                                .description("도로명 주소")
                                                .attributes(field("constraint", "필수 값")),
                                        fieldWithPath("foodCategories")
                                                .type(JsonFieldType.ARRAY)
                                                .description("음식 카테고리 배열")
                                                .attributes(field("constraint", "문자열 배열")),
                                        fieldWithPath("createdAt")
                                                .type(JsonFieldType.STRING)
                                                .description("가게 생성 시간")
                                                .attributes(field("constraint", "yyyy-MM-dd")),
                                        fieldWithPath("detailLocation")
                                                .type(JsonFieldType.STRING)
                                                .description("가게 상세 주소")
                                                .optional()
                                                .attributes(field("constraint", "최대 100자")),
                                        fieldWithPath("averageRating")
                                                .type(JsonFieldType.NUMBER)
                                                .description("가게 평균 평점")
                                                .attributes(field("constraint", "BigDecimal(1, 2)")),
                                        fieldWithPath("totalVisitCount")
                                                .type(JsonFieldType.NUMBER)
                                                .description("가게 총 리뷰 개수")
                                                .attributes(field("constraint", "Long")),
                                        fieldWithPath("lastModifiedMemberNickname")
                                                .type(JsonFieldType.STRING)
                                                .description("가게 정보 마지막 수정한 회원 닉네임")
                                                .optional()
                                                .attributes(field("constraint", "최대 20자")),
                                        fieldWithPath("owner")
                                                .type(JsonFieldType.OBJECT)
                                                .description("제보한 회원 정보"),
                                        fieldWithPath("owner.id")
                                                .type(JsonFieldType.NUMBER)
                                                .description("제보한 회원 ID"),
                                        fieldWithPath("owner.nickname")
                                                .type(JsonFieldType.STRING)
                                                .description("제보한 회원 닉네임")
                                )
                        )).andReturn();

        StoreResponse response = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                StoreResponse.class
        );

        assertThat(response).usingRecursiveComparison()
                .isEqualTo(storeResponse);

        then(storeService).should(times(1)).getStore(anyLong(), any(BigDecimal.class), any(BigDecimal.class));
    }

    @Test
    @DisplayName("회원은 가게 정보를 수정할 수 있다.")
    void updateStore() throws Exception {
        // given
        StoreUpdateRequest storeUpdateRequest = new StoreUpdateRequest(
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

        doNothing().when(storeService).updateStore(anyLong(), anyLong(), any(StoreUpdateRequest.class));

        // when
        ResultActions resultActions = performPutRequest(storeUpdateRequest, 1L);

        // then
        verify(storeService).updateStore(anyLong(), eq(1L), any(StoreUpdateRequest.class));

        resultActions
                .andExpect(status().isNoContent())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("storeId")
                                                .description("가게 ID")
                                ),
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
                                                .attributes(field("constraint", "HH:mm"))
                                                .optional(),
                                        fieldWithPath("closeTime")
                                                .type(JsonFieldType.STRING)
                                                .description("마감 시간")
                                                .attributes(field("constraint", "HH:mm"))
                                                .optional(),
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
                                                .attributes(field("constraint", "월,화,수,목,금,토,일 중 복수 선택가능"))
                                                .optional(),
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
                                )
                        )
                );

        then(storeService).should(times(1)).updateStore(anyLong(), eq(1L), any(StoreUpdateRequest.class));
    }

    @Test
    @DisplayName("회원은 가게 정보를 삭제할 수 있다.")
    void deleteStore() throws Exception {
        // given
        doNothing().when(storeService).deleteStore(anyLong(), anyLong());

        // when
        ResultActions resultActions = performDeleteRequest(1L);

        // then
        verify(storeService).deleteStore(anyLong(), eq(1L));

        resultActions
                .andExpect(status().isNoContent())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("storeId")
                                                .description("가게 ID")
                                )
                        )
                );

        then(storeService).should(times(1)).deleteStore(anyLong(), eq(1L));
    }

}