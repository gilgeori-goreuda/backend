package com.pd.gilgeorigoreuda.login.jwt;

import com.pd.gilgeorigoreuda.login.domain.MemberAccessRefreshToken;
import com.pd.gilgeorigoreuda.login.exception.ExpiredPeriodAccessTokenException;
import com.pd.gilgeorigoreuda.login.exception.ExpiredPeriodRefreshTokenException;
import com.pd.gilgeorigoreuda.login.exception.InvalidAccessTokenException;
import com.pd.gilgeorigoreuda.login.exception.InvalidRefreshTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {

    public static final String EMPTY_SUBJECT = "";

    private final SecretKey secretKey;
    private final Long accessExpirationTime;
    private final Long refreshExpirationTime;

    public JwtProvider(
            @Value("${security.jwt.secret-key}") final String secretKey,
            @Value("${security.jwt.access-expiration-time}") final Long accessExpirationTime,
            @Value("${security.jwt.refresh-expiration-time}") final Long refreshExpirationTime
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessExpirationTime = accessExpirationTime;
        this.refreshExpirationTime = refreshExpirationTime;
    }

    public MemberAccessRefreshToken generateLoginToken(final String subject) {
        String accessToken = createToken(subject, accessExpirationTime);
        String refreshToken = createToken(EMPTY_SUBJECT, refreshExpirationTime);

        return MemberAccessRefreshToken.of(accessToken, refreshToken);
    }

    private String createToken(final String subject, final Long expirationTime) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public void validateTokens(final MemberAccessRefreshToken memberAccessRefreshToken) {
        validateRefreshToken(memberAccessRefreshToken.getRefreshToken());
        validateAccessToken(memberAccessRefreshToken.getAccessToken());
    }

    private void validateRefreshToken(final String refreshToken) {
        try {
            parseToken(refreshToken);
        } catch (final ExpiredJwtException e) {
            throw new ExpiredPeriodRefreshTokenException();
        } catch (final JwtException | IllegalArgumentException e) {
            throw new InvalidRefreshTokenException();
        }
    }

    private void validateAccessToken(final String accessToken) {
        try {
            parseToken(accessToken);
        } catch (final ExpiredJwtException e) {
            throw new ExpiredPeriodAccessTokenException();
        } catch (final JwtException | IllegalArgumentException e) {
            throw new InvalidAccessTokenException();
        }
    }

    public String getSubject(final String token) {
        return parseToken(token)
                .getBody()
                .getSubject();
    }

    private Jws<Claims> parseToken(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
    }

    public boolean isValidRefreshButInvalidAccessToken(final String accessToken, final String refreshToken) {
        validateRefreshToken(refreshToken);

        try {
            validateAccessToken(accessToken);
        } catch (final ExpiredPeriodAccessTokenException e) {
            return true;
        }

        return false;
    }

    public boolean isValidRefreshAndValidAccessToken(final String accessToken, final String refreshToken) {
        try {
            validateRefreshToken(refreshToken);
            validateAccessToken(accessToken);

            return true;
        } catch (final JwtException e) {
            return false;
        }
    }

    public String regenerateAccessToken(final String subject) {
        return createToken(subject, accessExpirationTime);
    }

}
