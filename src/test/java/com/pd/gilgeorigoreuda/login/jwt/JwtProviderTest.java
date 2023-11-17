package com.pd.gilgeorigoreuda.login.jwt;

import com.pd.gilgeorigoreuda.common.exception.ExceptionType;
import com.pd.gilgeorigoreuda.login.domain.MemberToken;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JwtProviderTest {

    private static final Long EXPIRATION_TIME = 60000L;
    private static final Long EXPIRED_TIME = 0L;
    private static final String SUBJECT = "thisIsSampleSubject";
    private static final String INVALID_SECRET_KEY = "SAD21sdSsa@cJKsjnnjdjn@jsnakjn@JMNSDJsjnn@KNJKSd";

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Autowired
    JwtProvider jwtProvider;

    private MemberToken getMemberToken() {
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
        MemberToken memberTokens = getMemberToken();

        // when, then
        assertThat(jwtProvider.getSubject(memberTokens.getAccessToken())).isEqualTo(SUBJECT);
        assertThat(jwtProvider.getSubject(memberTokens.getRefreshToken())).isNull();
    }

    @DisplayName("AccessToken과 RefreshToken이 모두 유효한 토큰일 때 검증로직 성공")
    @Test
    void validateTokenSuccess() {
        // given
        final MemberToken memberToken = getMemberToken();

        // when & then
        assertDoesNotThrow(() -> jwtProvider.validateTokens(memberToken));
    }

    @Test
    @DisplayName("RefreshToken의 기한이 만료되었을 때 예외 발생")
    void validateTokenExpiredPeriodRefreshToken() {
        // given
        String refreshToken = makeJwt(EXPIRED_TIME, SUBJECT, secretKey);
        String accessToken = makeJwt(EXPIRATION_TIME, SUBJECT, secretKey);
        MemberToken memberToken = MemberToken.of(refreshToken, accessToken);

        // when & then
        assertThatThrownBy(() -> jwtProvider.validateTokens(memberToken))
                .isInstanceOf(ExpiredPeriodRefreshTokenException.class);
    }

    @Test
    @DisplayName("RefreshToken이 올바르지 않은 형식일 때 예외 발생")
    void validateTokenInvalidRefreshToken() {
        // given
        String refreshToken = makeJwt(EXPIRATION_TIME, SUBJECT, INVALID_SECRET_KEY);
        String accessToken = makeJwt(EXPIRATION_TIME, SUBJECT, secretKey);
        MemberToken memberToken = MemberToken.of(refreshToken, accessToken);

        // when & then
        assertThatThrownBy(() -> jwtProvider.validateTokens(memberToken))
                .isInstanceOf(InvalidRefreshTokenException.class);
    }

    @Test
    @DisplayName("AccessToken의 기한이 만료되었을 때 예외 발생")
    void validateExpiredPeriodAccessToken() {
        // given
        String refreshToken = makeJwt(EXPIRATION_TIME, SUBJECT, secretKey);
        String accessToken = makeJwt(EXPIRED_TIME, SUBJECT, secretKey);
        MemberToken memberToken = MemberToken.of(refreshToken, accessToken);

        // when & then
        assertThatThrownBy(() -> jwtProvider.validateTokens(memberToken))
                .isInstanceOf(ExpiredPeriodAccessTokenException.class);
    }

    @Test
    @DisplayName("AccessToken이 올바르지 않은 형식일 때 예외 발생")
    void validateTokenInvalidAccessToken() {
        // given
        String refreshToken = makeJwt(EXPIRATION_TIME, SUBJECT, secretKey);
        String accessToken = makeJwt(EXPIRATION_TIME, SUBJECT, INVALID_SECRET_KEY);
        MemberToken memberToken = MemberToken.of(refreshToken, accessToken);

        // when & then
        assertThatThrownBy(() -> jwtProvider.validateTokens(memberToken))
                .isInstanceOf(InvalidAccessTokenException.class);
    }

    @Test
    @DisplayName("RefreshToken이 유효하고 AccessToken의 유효기간이 지났다면 true를 반환한다.")
    void isValidRefreshButInvalidAccessToken() {
        // given
        String refreshToken = makeJwt(EXPIRATION_TIME, SUBJECT, secretKey);
        String accessToken = makeJwt(EXPIRED_TIME, SUBJECT, secretKey);

        // when & then
        assertThat(jwtProvider.isValidRefreshButInvalidAccessToken(refreshToken, accessToken))
                .isTrue();
    }

    @Test
    @DisplayName("RefreshToken이 올바르지 않으면 예외 발생")
    void isValidRefreshButInvalidAccessWhenInvalidRefreshTokenShouldThrowException() {
        // given
        String refreshToken = makeJwt(EXPIRATION_TIME, SUBJECT, INVALID_SECRET_KEY);
        String accessToken = makeJwt(EXPIRED_TIME, SUBJECT, secretKey);

        // when & then
        assertThatThrownBy(() -> jwtProvider.isValidRefreshButInvalidAccessToken(refreshToken, accessToken))
                .isInstanceOf(InvalidRefreshTokenException.class);
    }

    @Test
    @DisplayName("AccessToken이 올바르지 않으면 예외 발생")
    void isValidRefreshButInvalidAccessWhenInvalidAccessTokenShouldThrowException() {
        // given
        String refreshToken = makeJwt(EXPIRATION_TIME, SUBJECT, secretKey);
        String accessToken = makeJwt(EXPIRED_TIME, SUBJECT, INVALID_SECRET_KEY);

        // when & then
        assertThatThrownBy(() -> jwtProvider.isValidRefreshButInvalidAccessToken(refreshToken, accessToken))
                .isInstanceOf(InvalidAccessTokenException.class);
    }

    @Test
    @DisplayName("RefreshToken이 만료되어 있으면 예외 발생")
    void isValidRefreshButInvalidAccessWhenExpiredRefreshTokenShouldThrowException() {
        // given
        String refreshToken = makeJwt(EXPIRED_TIME, SUBJECT, secretKey);
        String accessToken = makeJwt(EXPIRED_TIME, SUBJECT, INVALID_SECRET_KEY);

        // when & then
        assertThatThrownBy(() -> jwtProvider.isValidRefreshButInvalidAccessToken(refreshToken, accessToken))
                .isInstanceOf(ExpiredPeriodRefreshTokenException.class);
    }

    @Test
    @DisplayName("RefreshToken과 AccessToken이 모두 유효하면 true를 반환한다.")
    void isValidRefreshAndValidAccessToken() {
        // given
        String refreshToken = makeJwt(EXPIRATION_TIME, SUBJECT, secretKey);
        String accessToken = makeJwt(EXPIRATION_TIME, SUBJECT, secretKey);

        // when & then
        assertThat(jwtProvider.isValidRefreshAndValidAccessToken(refreshToken, accessToken))
                .isTrue();
    }

}
