package com.pd.gilgeorigoreuda.auth.resolver;

import com.pd.gilgeorigoreuda.auth.MemberInfo;
import com.pd.gilgeorigoreuda.auth.domain.LoginMember;
import com.pd.gilgeorigoreuda.auth.exception.RefreshTokenNotFoundException;
import com.pd.gilgeorigoreuda.login.domain.MemberAccessRefreshToken;
import com.pd.gilgeorigoreuda.login.domain.MemberToken;
import com.pd.gilgeorigoreuda.login.exception.InvalidAccessTokenException;
import com.pd.gilgeorigoreuda.login.jwt.BearerTokenExtractor;
import com.pd.gilgeorigoreuda.login.jwt.JwtProvider;
import com.pd.gilgeorigoreuda.login.repository.MemberTokenRepository;
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
    private final MemberTokenRepository memberTokenRepository;

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
        String accessToken = bearerTokenExtractor.extractAccessToken(webRequest.getHeader(HttpHeaders.AUTHORIZATION));
        MemberToken memberToken = getMemberTokenByAccessToken(accessToken);

        jwtProvider.validateTokens(MemberAccessRefreshToken.of(memberToken.getAccessToken(), accessToken));

        Long memberId = Long.valueOf(jwtProvider.getSubject(accessToken));

        return LoginMember.member(memberId);
    }

    private MemberToken getMemberTokenByAccessToken(final String accessToken) {
        return memberTokenRepository.findByAccessToken(accessToken)
                .orElseThrow(InvalidAccessTokenException::new);
    }

}
