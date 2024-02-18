package com.pd.gilgeorigoreuda.login.controller;

import com.pd.gilgeorigoreuda.login.domain.MemberAccessRefreshToken;
import com.pd.gilgeorigoreuda.login.domain.MemberToken;
import com.pd.gilgeorigoreuda.login.dto.request.LoginRequest;
import com.pd.gilgeorigoreuda.login.service.LoginService;
import com.pd.gilgeorigoreuda.settings.ControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

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

@WebMvcTest(LoginController.class)
@AutoConfigureRestDocs
class LoginControllerTest extends ControllerTest {

    private static final MemberAccessRefreshToken MEMBER_ACCESS_REFRESH_TOKEN = MemberAccessRefreshToken.of("refreshToken", "accessToken");
    private static final MemberToken MEMBER_TOKEN = MemberToken.of(1L, "accessToken", "refreshToken");

    @MockBean
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        given(memberTokenRepository.findByAccessToken(any())).willReturn(Optional.of(MEMBER_TOKEN));
        doNothing().when(jwtProvider).validateTokens(any());
        given(jwtProvider.getSubject(any())).willReturn("1");
    }

    private ResultActions performLoginRequest(final String provider, final LoginRequest loginRequest) throws Exception {
        return mockMvc.perform(
                post("/login/{provider}", provider)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest))
        );
    }

    private ResultActions performExtendLoginRequest(final String accessToken) throws Exception {
        return mockMvc.perform(
                post("/token")
                        .header(AUTHORIZATION, MEMBER_ACCESS_REFRESH_TOKEN.getAccessToken())
        );
    }

    @Test
    @DisplayName("로그인 성공")
    void loginSuccess() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest("code");
        given(loginService.login(any(), any())).willReturn(MEMBER_ACCESS_REFRESH_TOKEN);

        // when
        ResultActions result = performLoginRequest("kakao", loginRequest);

        // then
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("accessToken").exists())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("provider").description("소셜 로그인 제공자")
                                ),
                                requestFields(
                                        fieldWithPath("code").description("인가 코드")
                                ),
                                responseFields(
                                        fieldWithPath("accessToken").description("액세스 토큰")
                                )
                        )
                );
    }

    @Test
    @DisplayName("토큰 갱신 성공")
    void extendLoginSuccess() throws Exception {
        // given
        given(loginService.regenerateAccessToken(any())).willReturn("newAccessToken");

        // when
        ResultActions result = performExtendLoginRequest(anyString());

        // then
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("accessToken").exists())
                .andDo(
                        restDocs.document(
                                requestHeaders(
                                        headerWithName(AUTHORIZATION).description("액세스 토큰")
                                ),
                                responseFields(
                                        fieldWithPath("accessToken").description("갱신된 액세스 토큰")
                                )
                        )
                );
    }

}