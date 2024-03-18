package com.pd.gilgeorigoreuda.visit.controller;

import com.pd.gilgeorigoreuda.login.domain.MemberAccessRefreshToken;
import com.pd.gilgeorigoreuda.login.domain.MemberToken;
import com.pd.gilgeorigoreuda.settings.ControllerTest;
import com.pd.gilgeorigoreuda.visit.dto.request.VisitRequest;
import com.pd.gilgeorigoreuda.visit.dto.request.VisitVerifyRequest;
import com.pd.gilgeorigoreuda.visit.dto.response.VisitResponse;
import com.pd.gilgeorigoreuda.visit.service.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

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

@WebMvcTest(VisitController.class)
@AutoConfigureRestDocs
class VisitControllerTest extends ControllerTest {

    private static final MemberAccessRefreshToken MEMBER_ACCESS_REFRESH_TOKEN = MemberAccessRefreshToken.of("accessToken", "refreshToken");
    private static final MemberToken MEMBER_TOKEN = MemberToken.of(1L, "accessToken", "refreshToken");

    private static final Long STORE_ID = 1L;

    @MockBean
    private VisitService visitService;

    @BeforeEach
    void setUp() {
        given(memberTokenRepository.findByAccessToken(any())).willReturn(Optional.of(MEMBER_TOKEN));
        doNothing().when(jwtProvider).validateTokens(any());
        given(jwtProvider.getSubject(any())).willReturn("1");
    }

    private ResultActions performPostVisitRequest(final VisitRequest visitRequest, final Long storeId) throws Exception {
        return mockMvc.perform(
                post("/api/v1/visit/stores/{storeId}", storeId)
                        .header(AUTHORIZATION, MEMBER_ACCESS_REFRESH_TOKEN.getAccessToken())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(visitRequest))
        );
    }

    private ResultActions performPostVisitVerifyRequest(final VisitVerifyRequest visitVerifyRequest, final Long storeId) throws Exception {
        return mockMvc.perform(
                post("/api/v1/visit/verification/stores/{storeId}", storeId)
                        .header(AUTHORIZATION, MEMBER_ACCESS_REFRESH_TOKEN.getAccessToken())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(visitVerifyRequest))
        );
    }

    @Test
    @DisplayName("방문 기록 생성 성공")
    void visitSuccess() throws Exception {
        // given
        VisitRequest visitRequest = new VisitRequest(
                new BigDecimal("37.1234567"),
                new BigDecimal("127.1234567")
        );

        VisitResponse visitResponse = new VisitResponse(
                1L,
                LocalDateTime.of(2022, 8, 1, 0, 0, 0),
                false,
                1000,
                3
        );

        given(visitService.visit(any(), any(), any())).willReturn(visitResponse);

        // when
        ResultActions resultActions = performPostVisitRequest(visitRequest, STORE_ID);

        // then
        MvcResult mvcResult = resultActions
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestHeaders(
                                        headerWithName(AUTHORIZATION).description("Access Token")
                                ),
                                pathParameters(
                                        parameterWithName("storeId").description("가게 ID")
                                ),
                                requestFields(
                                        fieldWithPath("lat")
                                                .type(JsonFieldType.NUMBER)
                                                .description("사용자의 위도")
                                                .attributes(field("constraint", "BigDecimal(3, 13)")),
                                        fieldWithPath("lng")
                                                .type(JsonFieldType.NUMBER)
                                                .description("사용자의 경도")
                                                .attributes(field("constraint", "BigDecimal(3, 13)"))
                                ),
                                responseFields(
                                        fieldWithPath("visitRecordId").description("방문 기록 ID")
                                                .type(JsonFieldType.NUMBER),
                                        fieldWithPath("visitRecordTime").description("방문 기록 시간")
                                                .type(JsonFieldType.STRING),
                                        fieldWithPath("isVisited").description("방문 여부")
                                                .type(JsonFieldType.BOOLEAN),
                                        fieldWithPath("walkingDistance").description("도보 이동 거리")
                                                .type(JsonFieldType.NUMBER),
                                        fieldWithPath("approximateWalkingTime").description("도보 이동 시간")
                                                .type(JsonFieldType.NUMBER)
                                )
                        )
                ).andReturn();

        VisitResponse response = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                VisitResponse.class
        );

        assertThat(response).usingRecursiveComparison()
                .isEqualTo(visitResponse);

        then(visitService).should(times(1)).visit(any(), any(), any());
    }

    @Test
    @DisplayName("위도 또는 경도가 없는 경우 방문 기록 생성 실패")
    void visitFailWhenLatOrLngIsNull() throws Exception {
        // given
        final Long storeId = 1L;

        VisitRequest visitRequest = new VisitRequest(
                null,
                new BigDecimal("127.1234567")
        );

        // when
        ResultActions resultActions = performPostVisitRequest(visitRequest, storeId);

        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("lat : 위도를 입력해주세요. (request value: null)"));

        then(visitService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("유효하지 않은 경도 또는 위도인 경우 방문 기록 생성 실패")
    void visitFailInvalidLng() throws Exception {
        // given
        VisitRequest visitRequest = new VisitRequest(
                new BigDecimal("37.1234567"),
                new BigDecimal("-127.1234567")
        );

        // when
        ResultActions resultActions = performPostVisitRequest(visitRequest, STORE_ID);

        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("lng : 음수 값은 허용되지 않습니다. (request value: -127.1234567)"));

        then(visitService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("방문 기록 검증 성공")
    void verifyVisitSuccess() throws Exception {
        // given
        VisitVerifyRequest visitVerifyRequest = new VisitVerifyRequest(
                new BigDecimal("37.1234567"),
                new BigDecimal("127.1234567")
        );

        // when
        ResultActions resultActions = performPostVisitVerifyRequest(visitVerifyRequest, STORE_ID);

        // then
        resultActions
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestHeaders(
                                        headerWithName(AUTHORIZATION).description("Access Token")
                                ),
                                pathParameters(
                                        parameterWithName("storeId").description("가게 ID")
                                ),
                                requestFields(
                                        fieldWithPath("lat")
                                                .type(JsonFieldType.NUMBER)
                                                .description("사용자의 위도")
                                                .attributes(field("constraint", "BigDecimal(3, 13)")),
                                        fieldWithPath("lng")
                                                .type(JsonFieldType.NUMBER)
                                                .description("사용자의 경도")
                                                .attributes(field("constraint", "BigDecimal(3, 13)"))
                                )
                        )
                );

        then(visitService).should(times(1)).verifyVisit(any(), any(), any());
    }

}