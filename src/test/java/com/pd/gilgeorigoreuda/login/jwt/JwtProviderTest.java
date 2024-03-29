package com.pd.gilgeorigoreuda.login.jwt;

import com.pd.gilgeorigoreuda.login.domain.MemberAccessRefreshToken;
import com.pd.gilgeorigoreuda.login.exception.ExpiredPeriodAccessTokenException;
import com.pd.gilgeorigoreuda.login.exception.ExpiredPeriodRefreshTokenException;
import com.pd.gilgeorigoreuda.login.exception.InvalidAccessTokenException;
import com.pd.gilgeorigoreuda.login.exception.InvalidRefreshTokenException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoSettings;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@MockitoSettings
class JwtProviderTest {

    private static final Long EXPIRATION_TIME = 60000L;
    private static final Long EXPIRED_TIME = 0L;
    private static final String SUBJECT = "thisIsSampleSubject";
    private static final String INVALID_SECRET_KEY = "INVALID_SECRET_KEYINVALID_SECRET_KEYINVALID_SECRET_KEYINVALID_SECRET_KEYINVALID_SECRET_KEY";
    private static final String SECRET_KEY = "VALID_SECRET_KEYVALID_SECRET_KEYVALID_SECRET_KEYVALID_SECRET_KEYVALID_SECRET_KEY";

    @InjectMocks
    private JwtProvider jwtProvider = new JwtProvider(SECRET_KEY, EXPIRATION_TIME, EXPIRATION_TIME);

    private MemberAccessRefreshToken getMemberToken() {
        return jwtProvider.generateLoginToken(SUBJECT);
    }

    private String makeJwt(final Long expirationTime, final String subject, final String secretKey) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(
                        Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)),
                        SignatureAlgorithm.HS256
                )
                .compact();
    }

    @Test
    @DisplayName("AccessToken과 RefreshToken 생성")
    void generateLoginToken() {
        // given
        MemberAccessRefreshToken memberAccessRefreshTokens = getMemberToken();

        // when, then
        assertThat(jwtProvider.getSubject(memberAccessRefreshTokens.getAccessToken())).isEqualTo(SUBJECT);
        assertThat(jwtProvider.getSubject(memberAccessRefreshTokens.getRefreshToken())).isNull();
    }

    @Test
    @DisplayName("RefreshToken의 기한이 만료되었을 때 예외 발생")
    void validateTokenExpiredPeriodRefreshToken() {
        // given
        String accessToken = makeJwt(EXPIRATION_TIME, SUBJECT, SECRET_KEY);
        String refreshToken = makeJwt(EXPIRED_TIME, SUBJECT, SECRET_KEY);
        MemberAccessRefreshToken memberAccessRefreshToken = MemberAccessRefreshToken.of(accessToken, refreshToken);

        // when & then
        assertThatThrownBy(() -> jwtProvider.validateTokens(memberAccessRefreshToken))
                .isInstanceOf(ExpiredPeriodRefreshTokenException.class);
    }

    @Test
    @DisplayName("RefreshToken이 올바르지 않은 형식일 때 예외 발생")
    void validateTokenInvalidRefreshToken() {
        // given
        String accessToken = makeJwt(EXPIRATION_TIME, SUBJECT, SECRET_KEY);
        String refreshToken = makeJwt(EXPIRATION_TIME, SUBJECT, INVALID_SECRET_KEY);
        MemberAccessRefreshToken memberAccessRefreshToken = MemberAccessRefreshToken.of(accessToken, refreshToken);

        // when & then
        assertThatThrownBy(() -> jwtProvider.validateTokens(memberAccessRefreshToken))
                .isInstanceOf(InvalidRefreshTokenException.class);
    }

    @Test
    @DisplayName("AccessToken의 기한이 만료되었을 때 예외 발생")
    void validateExpiredPeriodAccessToken() {
        // given
        String accessToken = makeJwt(EXPIRED_TIME, SUBJECT, SECRET_KEY);
        String refreshToken = makeJwt(EXPIRATION_TIME, SUBJECT, SECRET_KEY);
        MemberAccessRefreshToken memberAccessRefreshToken = MemberAccessRefreshToken.of(accessToken, refreshToken);

        // when & then
        assertThatThrownBy(() -> jwtProvider.validateTokens(memberAccessRefreshToken))
                .isInstanceOf(ExpiredPeriodAccessTokenException.class);
    }

    @Test
    @DisplayName("AccessToken이 올바르지 않은 형식일 때 예외 발생")
    void validateTokenInvalidAccessToken() {
        // given
        String accessToken = makeJwt(EXPIRATION_TIME, SUBJECT, INVALID_SECRET_KEY);
        String refreshToken = makeJwt(EXPIRATION_TIME, SUBJECT, SECRET_KEY);
        MemberAccessRefreshToken memberAccessRefreshToken = MemberAccessRefreshToken.of(accessToken, refreshToken);

        // when & then
        assertThatThrownBy(() -> jwtProvider.validateTokens(memberAccessRefreshToken))
                .isInstanceOf(InvalidAccessTokenException.class);
    }

    @Test
    @DisplayName("RefreshToken이 유효하고 AccessToken의 유효기간이 지났다면 true를 반환한다.")
    void isValidRefreshButInvalidAccessToken() {
        // given
        String accessToken = makeJwt(EXPIRED_TIME, SUBJECT, SECRET_KEY);
        String refreshToken = makeJwt(EXPIRATION_TIME, SUBJECT, SECRET_KEY);

        // when & then
        assertThat(jwtProvider.isValidRefreshButInvalidAccessToken(accessToken, refreshToken))
                .isTrue();
    }

    @Test
    @DisplayName("RefreshToken이 올바르지 않으면 예외 발생")
    void isValidRefreshButInvalidAccessWhenInvalidRefreshTokenShouldThrowException() {
        // given
        String accessToken = makeJwt(EXPIRED_TIME, SUBJECT, SECRET_KEY);
        String refreshToken = makeJwt(EXPIRATION_TIME, SUBJECT, INVALID_SECRET_KEY);

        // when & then
        assertThatThrownBy(() -> jwtProvider.isValidRefreshButInvalidAccessToken(accessToken, refreshToken))
                .isInstanceOf(InvalidRefreshTokenException.class);
    }

    @Test
    @DisplayName("AccessToken이 올바르지 않으면 예외 발생")
    void isValidRefreshButInvalidAccessWhenInvalidAccessTokenShouldThrowException() {
        // given
        String accessToken = makeJwt(EXPIRED_TIME, SUBJECT, INVALID_SECRET_KEY);
        String refreshToken = makeJwt(EXPIRATION_TIME, SUBJECT, SECRET_KEY);

        // when & then
        assertThatThrownBy(() -> jwtProvider.isValidRefreshButInvalidAccessToken(accessToken, refreshToken))
                .isInstanceOf(InvalidAccessTokenException.class);
    }

    @Test
    @DisplayName("RefreshToken이 만료되어 있으면 예외 발생")
    void isValidRefreshButInvalidAccessWhenExpiredRefreshTokenShouldThrowException() {
        // given
        String accessToken = makeJwt(EXPIRED_TIME, SUBJECT, INVALID_SECRET_KEY);
        String refreshToken = makeJwt(EXPIRED_TIME, SUBJECT, SECRET_KEY);

        // when & then
        assertThatThrownBy(() -> jwtProvider.isValidRefreshButInvalidAccessToken(accessToken, refreshToken))
                .isInstanceOf(ExpiredPeriodRefreshTokenException.class);
    }

    @Test
    @DisplayName("RefreshToken과 AccessToken이 모두 유효하면 true를 반환한다.")
    void isValidRefreshAndValidAccessToken() {
        // given
        String accessToken = makeJwt(EXPIRATION_TIME, SUBJECT, SECRET_KEY);
        String refreshToken = makeJwt(EXPIRATION_TIME, SUBJECT, SECRET_KEY);

        // when & then
        assertThat(jwtProvider.isValidRefreshAndValidAccessToken(accessToken, refreshToken))
                .isTrue();
    }

    @Test
    @DisplayName("AccessToken과 RefreshToken이 모두 유효한 토큰일 때 검증로직 성공")
    void validateTokenSuccess() {
        // given
        final MemberAccessRefreshToken memberAccessRefreshToken = getMemberToken();

        // when & then
        assertDoesNotThrow(() -> jwtProvider.validateTokens(memberAccessRefreshToken));
    }

}
