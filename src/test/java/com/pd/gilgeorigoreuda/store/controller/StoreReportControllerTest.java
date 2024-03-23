package com.pd.gilgeorigoreuda.store.controller;

import com.pd.gilgeorigoreuda.login.domain.MemberAccessRefreshToken;
import com.pd.gilgeorigoreuda.login.domain.MemberToken;
import com.pd.gilgeorigoreuda.settings.ControllerTest;
import com.pd.gilgeorigoreuda.store.service.StoreReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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

@WebMvcTest(StoreReportController.class)
@AutoConfigureRestDocs
class StoreReportControllerTest extends ControllerTest {

    private static final MemberAccessRefreshToken MEMBER_ACCESS_REFRESH_TOKEN = MemberAccessRefreshToken.of("accessToken", "refreshToken");
    private static final MemberToken MEMBER_TOKEN = MemberToken.of(1L, "accessToken" ,"refreshToken");

    @MockBean
    private StoreReportService storeReportService;

    @BeforeEach
    void setUp() {
        given(memberTokenRepository.findByAccessToken(any())).willReturn(Optional.of(MEMBER_TOKEN));
        doNothing().when(jwtProvider).validateTokens(any());
        given(jwtProvider.getSubject(any())).willReturn("1");
    }

}