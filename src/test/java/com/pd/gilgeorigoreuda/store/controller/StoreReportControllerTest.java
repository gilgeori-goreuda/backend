package com.pd.gilgeorigoreuda.store.controller;

import com.pd.gilgeorigoreuda.login.domain.MemberAccessRefreshToken;
import com.pd.gilgeorigoreuda.login.domain.MemberToken;
import com.pd.gilgeorigoreuda.settings.ControllerTest;
import com.pd.gilgeorigoreuda.store.dto.request.ReportCreateRequest;
import com.pd.gilgeorigoreuda.store.dto.response.StoreReportHistoryListResponse;
import com.pd.gilgeorigoreuda.store.dto.response.StoreReportHistoryResponse;
import com.pd.gilgeorigoreuda.store.dto.response.StoreReportMemberResponse;
import com.pd.gilgeorigoreuda.store.dto.response.StoreReportStoreResponse;
import com.pd.gilgeorigoreuda.store.service.StoreReportService;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.pd.gilgeorigoreuda.settings.restdocs.RestDocsConfiguration.*;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;


@WebMvcTest(StoreReportController.class)
@AutoConfigureRestDocs
class StoreReportControllerTest extends ControllerTest {

    private static final MemberAccessRefreshToken MEMBER_ACCESS_REFRESH_TOKEN = MemberAccessRefreshToken.of("accessToken", "refreshToken");
    private static final MemberToken MEMBER_TOKEN = MemberToken.of(1L, "accessToken", "refreshToken");

    @MockBean
    private StoreReportService storeReportService;

    @BeforeEach
    void setUp() {
        given(memberTokenRepository.findByAccessToken(any())).willReturn(Optional.of(MEMBER_TOKEN));
        doNothing().when(jwtProvider).validateTokens(any());
        given(jwtProvider.getSubject(any())).willReturn("1");
    }

    private ResultActions performCheckMemberReportList() throws Exception {
        return mockMvc.perform(
                get("/api/v1/reports/stores/memberCheck")
                        .header(AUTHORIZATION, MEMBER_ACCESS_REFRESH_TOKEN.getAccessToken())
                        .contentType(APPLICATION_JSON)
        );
    }

    private ResultActions performCheckStoreReportList(final Long storeId) throws Exception {
        return mockMvc.perform(
                get("/api/v1/reports/stores/storeCheck/{storeId}", storeId)
                        .header(AUTHORIZATION, MEMBER_ACCESS_REFRESH_TOKEN.getAccessToken())
                        .contentType(APPLICATION_JSON)
        );
    }

    private ResultActions performCreateStoreReport(final ReportCreateRequest reportCreateRequest, final Long storeId) throws Exception {
        return mockMvc.perform(
                post("/api/v1/reports/stores/{storeId}", storeId)
                        .header(AUTHORIZATION, MEMBER_ACCESS_REFRESH_TOKEN.getAccessToken())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reportCreateRequest))
        );
    }

    private ResultActions performDeleteStoreReport(final Long reportId) throws Exception {
        return mockMvc.perform(
                delete("/api/v1/reports/stores/{reportId}", reportId)
                        .header(AUTHORIZATION, MEMBER_ACCESS_REFRESH_TOKEN.getAccessToken())
                        .contentType(APPLICATION_JSON)
        );
    }


    @Test
    @DisplayName("가게 신고 생성 성공")
    void createStoreReportSuccess() throws Exception {
        // given
        Long storeId = 1L;
        ReportCreateRequest reportCreateRequest = new ReportCreateRequest(
                "가게 신고 내용",
                new BigDecimal("37.123456"),
                new BigDecimal("127.123456")
        );


        // when
        ResultActions resultActions = performCreateStoreReport(reportCreateRequest, storeId);

        // then
        resultActions
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("storeId").description("가게 ID")
                                ),
                                requestHeaders(
                                        headerWithName(AUTHORIZATION).description("Access Token")
                                ),
                                requestFields(
                                        fieldWithPath("content")
                                                .type(JsonFieldType.STRING)
                                                .description("신고 내용")
                                                .attributes(field("constraints", "1에서 300자 사이")),
                                        fieldWithPath("memberLat")
                                                .type(JsonFieldType.NUMBER)
                                                .description("사용자 위치 위도")
                                                .attributes(field("constraint", "BigDecimal(3, 13)")),
                                        fieldWithPath("memberLng")
                                                .type(JsonFieldType.NUMBER)
                                                .description("사용자 위치 경도")
                                                .attributes(field("constraint", "BigDecimal(3, 13)"))
                                )
                        )
                );

        then(storeReportService).should(times(1)).createStoreReport(any(), any(), any());
    }

    @Nested
    @DisplayName("가게 신고 생성 데이터 검증 실패")
    class createStoreReportFail {

        @Test
        @DisplayName("가게 신고 내용이 blank인 경우 예외가 발생한다.")
        void whenReportContentIsBlank() throws Exception {
            // given
            Long storeId = 1L;
            ReportCreateRequest badReportCreateRequest = new ReportCreateRequest(
                    "",
                    new BigDecimal("37.123456"),
                    new BigDecimal("127.123456")
            );


            // when
            ResultActions resultActions = performCreateStoreReport(badReportCreateRequest, storeId);

            // then
            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("content : 신고 내용을 입력해주세요. (request value: )"));

            then(storeReportService).should(never()).createStoreReport(any(), any(), any());
        }

        @Test
        @DisplayName("가게 신고 내용이 null인 경우 예외가 발생한다.")
        void whenReportContentIsNull() throws Exception {
            // given
            Long storeId = 1L;
            ReportCreateRequest badReportCreateRequest = new ReportCreateRequest(
                    null,
                    new BigDecimal("37.123456"),
                    new BigDecimal("127.123456")
            );


            // when
            ResultActions resultActions = performCreateStoreReport(badReportCreateRequest, storeId);

            // then
            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("content : 신고 내용을 입력해주세요. (request value: null)"));

            then(storeReportService).should(never()).createStoreReport(any(), any(), any());
        }

        @Test
        @DisplayName("가게 신고 내용이 300자를 초과하는 경우 예외가 발생한다.")
        void whenReportContentIsOver300() throws Exception {
            // given
            Long storeId = 1L;
            ReportCreateRequest badReportCreateRequest = new ReportCreateRequest(
                    "가게 신고 내용".repeat(50),
                    new BigDecimal("37.123456"),
                    new BigDecimal("127.123456")
            );


            // when
            ResultActions resultActions = performCreateStoreReport(badReportCreateRequest, storeId);

            // then
            resultActions
                    .andExpect(status().isBadRequest());
        }

    }

    @Test
    @DisplayName("가게 신고 삭제 성공")
    void deleteStoreReportSuccess() throws Exception {
        // given
        Long reportId = 1L;

        // when
        ResultActions resultActions = performDeleteStoreReport(reportId);

        // then
        resultActions
                .andExpect(status().isNoContent())
                .andDo(
                        restDocs.document(
                                requestHeaders(
                                        headerWithName(AUTHORIZATION).description("Access Token")
                                ),
                                pathParameters(
                                        parameterWithName("reportId").description("신고 ID")
                                )
                        )
                );

        then(storeReportService).should(times(1)).deleteReport(any(), any());
    }

    @Test
    @DisplayName("사용자가 신고했던 가게 리스트 조회")
    void checkMemberReportList() throws Exception {
        //given
        StoreReportHistoryListResponse storeReportHistoryListResponse = StoreReportHistoryListResponse.of(
                List.of(
                        new StoreReportHistoryResponse(
                                1L,
                                "가게 신고 내용",
                                new StoreReportMemberResponse(1L, "nickname", "profileImageUrl"),
                                new StoreReportStoreResponse(1L, "storeName", "storeImageUrl"),
                                LocalDateTime.of(2021, 10, 1, 0, 0, 0),
                                LocalDateTime.of(2021, 10, 1, 0, 0, 0)
                        ),
                        new StoreReportHistoryResponse(
                                2L,
                                "가게 신고 내용",
                                new StoreReportMemberResponse(1L, "nickname", "profileImageUrl"),
                                new StoreReportStoreResponse(1L, "storeName", "storeImageUrl"),
                                LocalDateTime.of(2022, 10, 1, 0, 0, 0),
                                LocalDateTime.of(2022, 10, 1, 0, 0, 0)
                        )
                )
        );

        given(storeReportService.checkMemberReportList(anyLong())).willReturn(storeReportHistoryListResponse);

        // when
        ResultActions resultActions = performCheckMemberReportList();

        // then
        MvcResult mvcResult = resultActions
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestHeaders(
                                        headerWithName(AUTHORIZATION).description("Access Token")
                                ),
                                responseFields(
                                        fieldWithPath("results[].id")
                                                .type(JsonFieldType.NUMBER)
                                                .description("신고 ID"),
                                        fieldWithPath("results[].content")
                                                .type(JsonFieldType.STRING)
                                                .description("신고 내용"),
                                        fieldWithPath("results[].member")
                                                .type(JsonFieldType.OBJECT)
                                                .description("신고자 정보"),
                                        fieldWithPath("results[].member.id")
                                                .type(JsonFieldType.NUMBER)
                                                .description("신고자 ID"),
                                        fieldWithPath("results[].member.nickname")
                                                .type(JsonFieldType.STRING)
                                                .description("신고자 닉네임"),
                                        fieldWithPath("results[].member.profileImageUrl")
                                                .type(JsonFieldType.STRING)
                                                .description("신고자 프로필 이미지 URL"),
                                        fieldWithPath("results[].store")
                                                .type(JsonFieldType.OBJECT)
                                                .description("가게 정보"),
                                        fieldWithPath("results[].store.id")
                                                .type(JsonFieldType.NUMBER)
                                                .description("가게 ID"),
                                        fieldWithPath("results[].store.name")
                                                .type(JsonFieldType.STRING)
                                                .description("가게 이름"),
                                        fieldWithPath("results[].store.imageUrl")
                                                .type(JsonFieldType.STRING)
                                                .description("가게 이미지 URL"),
                                        fieldWithPath("results[].createdAt")
                                                .type(JsonFieldType.STRING)
                                                .description("신고 생성 시간"),
                                        fieldWithPath("results[].modifiedAt")
                                                .type(JsonFieldType.STRING)
                                                .description("신고 수정 시간")
                                )
                        )
                ).andReturn();

        StoreReportHistoryListResponse response = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                StoreReportHistoryListResponse.class
        );

        assertThat(response).usingRecursiveComparison()
                .isEqualTo(storeReportHistoryListResponse);

        then(storeReportService).should(times(1)).checkMemberReportList(any());
    }

    @Test
    @DisplayName("해당 가게 신고 리스트 조회")
    void checkStoreReportList() throws Exception {
        //given
        Long storeId = 1L;
        StoreReportHistoryListResponse storeReportHistoryListResponse = StoreReportHistoryListResponse.of(
                List.of(
                        new StoreReportHistoryResponse(
                                1L,
                                "가게 신고 내용",
                                new StoreReportMemberResponse(1L, "nickname", "profileImageUrl"),
                                new StoreReportStoreResponse(1L, "storeName", "storeImageUrl"),
                                LocalDateTime.of(2021, 10, 1, 0, 0, 0),
                                LocalDateTime.of(2021, 10, 1, 0, 0, 0)
                        ),
                        new StoreReportHistoryResponse(
                                2L,
                                "가게 신고 내용",
                                new StoreReportMemberResponse(1L, "nickname", "profileImageUrl"),
                                new StoreReportStoreResponse(1L, "storeName", "storeImageUrl"),
                                LocalDateTime.of(2022, 10, 1, 0, 0, 0),
                                LocalDateTime.of(2022, 10, 1, 0, 0, 0)
                        )
                )
        );

        given(storeReportService.checkStoreReportList(anyLong())).willReturn(storeReportHistoryListResponse);

        // when
        ResultActions resultActions = performCheckStoreReportList(storeId);

        // then
        MvcResult mvcResult = resultActions
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("storeId").description("가게 ID")
                                ),
                                requestHeaders(
                                        headerWithName(AUTHORIZATION).description("Access Token")
                                ),
                                responseFields(
                                        fieldWithPath("results[].id")
                                                .type(JsonFieldType.NUMBER)
                                                .description("신고 ID"),
                                        fieldWithPath("results[].content")
                                                .type(JsonFieldType.STRING)
                                                .description("신고 내용"),
                                        fieldWithPath("results[].member")
                                                .type(JsonFieldType.OBJECT)
                                                .description("신고자 정보"),
                                        fieldWithPath("results[].member.id")
                                                .type(JsonFieldType.NUMBER)
                                                .description("신고자 ID"),
                                        fieldWithPath("results[].member.nickname")
                                                .type(JsonFieldType.STRING)
                                                .description("신고자 닉네임"),
                                        fieldWithPath("results[].member.profileImageUrl")
                                                .type(JsonFieldType.STRING)
                                                .description("신고자 프로필 이미지 URL"),
                                        fieldWithPath("results[].store")
                                                .type(JsonFieldType.OBJECT)
                                                .description("가게 정보"),
                                        fieldWithPath("results[].store.id")
                                                .type(JsonFieldType.NUMBER)
                                                .description("가게 ID"),
                                        fieldWithPath("results[].store.name")
                                                .type(JsonFieldType.STRING)
                                                .description("가게 이름"),
                                        fieldWithPath("results[].store.imageUrl")
                                                .type(JsonFieldType.STRING)
                                                .description("가게 이미지 URL"),
                                        fieldWithPath("results[].createdAt")
                                                .type(JsonFieldType.STRING)
                                                .description("신고 생성 시간"),
                                        fieldWithPath("results[].modifiedAt")
                                                .type(JsonFieldType.STRING)
                                                .description("신고 수정 시간")
                                )
                        )
                ).andReturn();

        StoreReportHistoryListResponse response = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                StoreReportHistoryListResponse.class
        );

        assertThat(response).usingRecursiveComparison()
                .isEqualTo(storeReportHistoryListResponse);

        then(storeReportService).should(times(1)).checkStoreReportList(any());
    }

}