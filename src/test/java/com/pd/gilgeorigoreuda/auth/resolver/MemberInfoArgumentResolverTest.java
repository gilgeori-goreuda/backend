package com.pd.gilgeorigoreuda.auth.resolver;

import com.pd.gilgeorigoreuda.auth.MemberInfo;
import com.pd.gilgeorigoreuda.auth.domain.Authority;
import com.pd.gilgeorigoreuda.auth.domain.LoginMember;
import com.pd.gilgeorigoreuda.login.domain.MemberToken;
import com.pd.gilgeorigoreuda.login.exception.InvalidAccessTokenException;
import com.pd.gilgeorigoreuda.login.jwt.BearerTokenExtractor;
import com.pd.gilgeorigoreuda.login.jwt.JwtProvider;
import com.pd.gilgeorigoreuda.login.repository.MemberTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@MockitoSettings
public class MemberInfoArgumentResolverTest {

    @InjectMocks
    private MemberInfoArgumentResolver memberInfoArgumentResolver;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private BearerTokenExtractor bearerTokenExtractor;

    @Mock
    private MemberTokenRepository memberTokenRepository;

    @Mock
    private NativeWebRequest webRequest;

    @Mock
    private ModelAndViewContainer mavContainer;

    @Mock
    private HandlerMethodArgumentResolver methodArgumentResolver;

    @BeforeEach
    void setUp() {
        this.memberInfoArgumentResolver = new MemberInfoArgumentResolver(jwtProvider, bearerTokenExtractor, memberTokenRepository);
    }

    @Test
    @DisplayName("MethodParameter에 MemberInfo 어노테이션이 붙어있으면 true를 반환한다.")
    void supportsParameterWhenParameterAnnotatedWithMemberInfoShouldReturnTrue() {
        MethodParameter parameter = mock(MethodParameter.class);
        when(parameter.hasParameterAnnotation(MemberInfo.class)).thenReturn(true);

        boolean result = memberInfoArgumentResolver.supportsParameter(parameter);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("MethodParameter에 MemberInfo 어노테이션이 붙어있지 않으면 false를 반환한다.")
    void supportsParameterWhenParameterNotAnnotatedWithMemberInfoShouldReturnFalse() {
        MethodParameter parameter = mock(MethodParameter.class);
        when(parameter.hasParameterAnnotation(MemberInfo.class)).thenReturn(false);

        boolean result = memberInfoArgumentResolver.supportsParameter(parameter);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("유효한 AccessToken 이라면 MemberTokenRepository에서 accessToken으로 MemberToken을 찾는다.")
    void resolveArgumentWhenValidAccessTokenShouldReturnLoginMember() {
        // Given
        String accessToken = "validAccessToken";
        MemberToken memberToken = new MemberToken(1L, accessToken, "validRefreshToken");
        when(bearerTokenExtractor.extractAccessToken(any())).thenReturn(accessToken);
        when(memberTokenRepository.findByAccessToken(accessToken)).thenReturn(Optional.of(memberToken));
        when(jwtProvider.getSubject(accessToken)).thenReturn("1");
        doNothing().when(jwtProvider).validateTokens(any());

        // When
        LoginMember loginMember = memberInfoArgumentResolver.resolveArgument(null, mavContainer, webRequest, null);

        // Then
        assertThat(loginMember)
                .isNotNull()
                .hasFieldOrPropertyWithValue("memberId", 1L)
                .hasFieldOrPropertyWithValue("authority", Authority.MEMBER);
    }

    @Test
    @DisplayName("유효하지 않은 AccessToken 이라면 InvalidAccessTokenException을 던진다.")
    void resolveArgument_WhenInvalidAccessToken_ShouldThrowInvalidAccessTokenException() {
        // Given
        String accessToken = "invalidAccessToken";
        when(bearerTokenExtractor.extractAccessToken(any())).thenReturn(accessToken);
        when(memberTokenRepository.findByAccessToken(accessToken)).thenReturn(Optional.empty());

        // When, Then
        assertThatExceptionOfType(InvalidAccessTokenException.class)
                .isThrownBy(() -> memberInfoArgumentResolver.resolveArgument(null, mavContainer, webRequest, null));
    }

}
