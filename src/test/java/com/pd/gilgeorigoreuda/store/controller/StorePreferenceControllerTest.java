package com.pd.gilgeorigoreuda.store.controller;

import com.pd.gilgeorigoreuda.login.domain.MemberAccessRefreshToken;
import com.pd.gilgeorigoreuda.login.domain.MemberToken;
import com.pd.gilgeorigoreuda.settings.ControllerTest;
import com.pd.gilgeorigoreuda.store.service.StorePreferenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StorePreferenceController.class)
@AutoConfigureRestDocs
class StorePreferenceControllerTest extends ControllerTest {

    private static final MemberAccessRefreshToken MEMBER_ACCESS_REFRESH_TOKEN = MemberAccessRefreshToken.of("accessToken", "refreshToken");
    private static final MemberToken MEMBER_TOKEN = MemberToken.of(1L, "accessToken" ,"refreshToken");

    @MockBean
    private StorePreferenceService storePreferenceService;

    @BeforeEach
    void setUp() {
        given(memberTokenRepository.findByAccessToken(any())).willReturn(Optional.of(MEMBER_TOKEN));
        doNothing().when(jwtProvider).validateTokens(any());
        given(jwtProvider.getSubject(any())).willReturn("1");
    }

    private ResultActions performPostLikeRequest(final Long storeId) throws Exception {
        return mockMvc.perform(
                post("/api/v1/preferences/stores/{storeId}/like", storeId)
                        .header(AUTHORIZATION, MEMBER_ACCESS_REFRESH_TOKEN.getAccessToken())
                        .contentType(APPLICATION_JSON)
        );
    }

    private ResultActions performPostHateRequest(final Long storeId) throws Exception {
        return mockMvc.perform(
                post("/api/v1/preferences/stores/{storeId}/hate", storeId)
                        .header(AUTHORIZATION, MEMBER_ACCESS_REFRESH_TOKEN.getAccessToken())
                        .contentType(APPLICATION_JSON)
        );
    }

    @Test
    @DisplayName("가게 좋아요 요청 성공")
    void addStoreLike() throws Exception {
        // given
        Long storeId = 1L;
        Long memberId = MEMBER_TOKEN.getMemberId();

        doNothing().when(storePreferenceService).storeLike(storeId, memberId);

        // when
        ResultActions resultActions = performPostLikeRequest(storeId);

        // then
        resultActions
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("storeId").description("가게 ID")
                                )
                        )
                );

        then(storePreferenceService).should(times(1)).storeLike(storeId, memberId);
    }

    @Test
    @DisplayName("가게 싫어요 요청 성공")
    void addStoreHate() throws Exception {
        // given
        Long storeId = 1L;
        Long memberId = MEMBER_TOKEN.getMemberId();

        doNothing().when(storePreferenceService).storeHate(storeId, memberId);

        // when
        ResultActions resultActions = performPostHateRequest(storeId);

        // then
        resultActions
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("storeId").description("가게 ID")
                                )
                        )
                );

        then(storePreferenceService).should(times(1)).storeHate(storeId, memberId);
    }

}