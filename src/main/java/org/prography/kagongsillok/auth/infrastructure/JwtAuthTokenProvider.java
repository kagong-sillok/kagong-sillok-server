package org.prography.kagongsillok.auth.infrastructure;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import org.prography.kagongsillok.auth.domain.AuthTokenProvider;
import org.prography.kagongsillok.auth.domain.dto.LoginMemberResult;
import org.prography.kagongsillok.auth.infrastructure.exception.JwtInvalidFormException;
import org.prography.kagongsillok.auth.infrastructure.exception.JwtInvalidSecretKeyException;
import org.prography.kagongsillok.auth.infrastructure.exception.JwtInvalidExpiredException;
import org.prography.kagongsillok.member.domain.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthTokenProvider implements AuthTokenProvider {

    private static final String ACCESS_TOKEN_SUBJECT = "kagongsillok access token";
    private static final String MEMBER_ID_KEY_NAME = "memberId";
    private static final String ROLE_KEY_NAME = "role";
    private static final String REFRESH_TOKEN_SUBJECT = "kagongsillok refresh token";

    private final SecretKey key;

    public JwtAuthTokenProvider(@Value("${security.jwt.token.secret-key}") final String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String createAccessToken(final Long memberId, final Role role, final ZonedDateTime accessTokenExpire) {
        final Date now = new Date();
        final Date expiration = Date.from(accessTokenExpire.toInstant());

        final Claims claims = Jwts.claims(
                Map.of(
                        MEMBER_ID_KEY_NAME, memberId,
                        ROLE_KEY_NAME, role.name()
                )
        );

        return Jwts.builder()
                .setSubject(ACCESS_TOKEN_SUBJECT)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String createRefreshToken(final Long memberId, final ZonedDateTime expireDateTime) {
        final Date now = new Date();
        final Date expiration = Date.from(expireDateTime.toInstant());

        final Claims claims = Jwts.claims(Map.of(MEMBER_ID_KEY_NAME, memberId));

        return Jwts.builder()
                .setSubject(REFRESH_TOKEN_SUBJECT)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public LoginMemberResult getLoginMember(final String token) {
        final Claims payload = tokenToJws(token).getBody();
        final Long memberId = payload.get(MEMBER_ID_KEY_NAME, Long.class);
        final String role = payload.get(ROLE_KEY_NAME, String.class);
        return new LoginMemberResult(memberId, role);
    }

    @Override
    public Long getMemberIdByRefreshToken(final String refreshTokenValue) {
        final Claims payload = tokenToJws(refreshTokenValue).getBody();
        return payload.get(MEMBER_ID_KEY_NAME, Long.class);
    }

    private Jws<Claims> tokenToJws(final String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
        } catch (final IllegalArgumentException | MalformedJwtException e) {
            throw new JwtInvalidFormException(token);
        } catch (final SignatureException e) {
            throw new JwtInvalidSecretKeyException(token);
        } catch (final ExpiredJwtException e) {
            throw new JwtInvalidExpiredException();
        }
    }
}
