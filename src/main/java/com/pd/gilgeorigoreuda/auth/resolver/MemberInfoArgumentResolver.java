package com.pd.gilgeorigoreuda.auth.resolver;

import com.pd.gilgeorigoreuda.auth.MemberInfo;
import com.pd.gilgeorigoreuda.auth.domain.LoginMember;
import com.pd.gilgeorigoreuda.auth.exception.RefreshTokenNotFoundException;
import com.pd.gilgeorigoreuda.login.domain.MemberToken;
import com.pd.gilgeorigoreuda.login.jwt.BearerTokenExtractor;
import com.pd.gilgeorigoreuda.login.jwt.JwtProvider;
import com.pd.gilgeorigoreuda.login.repository.RefreshTokenRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class MemberInfoArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String REFRESH_TOKEN = "refresh-token";

    private final JwtProvider jwtProvider;

    private final BearerTokenExtractor bearerTokenExtractor;

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.withContainingClass(Long.class)
                .hasParameterAnnotation(MemberInfo.class);
    }

    @Override
    public LoginMember resolveArgument(
            final MethodParameter parameter,
            final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest,
            final WebDataBinderFactory binderFactory
    ) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        String refreshToken = extractRefreshToken(request.getCookies());
        String accessToken = bearerTokenExtractor.extractAccessToken(webRequest.getHeader(HttpHeaders.AUTHORIZATION));

        jwtProvider.validateTokens(MemberToken.of(refreshToken, accessToken));

        Long memberId = Long.valueOf(jwtProvider.getSubject(accessToken));

        return LoginMember.member(memberId);
    }

    private String extractRefreshToken(final Cookie... cookies) {
        if (cookies == null) {
            throw new RefreshTokenNotFoundException();
        }

        return Arrays.stream(cookies)
                .filter(this::isValidRefreshToken)
                .findFirst()
                .orElseThrow(RefreshTokenNotFoundException::new)
                .getValue();
    }

    private boolean isValidRefreshToken(final Cookie cookie) {
        return REFRESH_TOKEN.equals(cookie.getName()) &&
                refreshTokenRepository.existsById(cookie.getValue());
    }

}
