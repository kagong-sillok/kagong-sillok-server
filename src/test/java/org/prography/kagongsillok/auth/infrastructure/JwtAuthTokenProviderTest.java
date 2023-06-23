package org.prography.kagongsillok.auth.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.auth.domain.dto.LoginMemberInfo;
import org.prography.kagongsillok.auth.infrastructure.exception.JwtInvalidFormException;
import org.prography.kagongsillok.auth.infrastructure.exception.JwtInvalidSecretKeyException;
import org.prography.kagongsillok.auth.infrastructure.exception.JwtInvalidExpiredException;
import org.prography.kagongsillok.member.domain.Role;

class JwtAuthTokenProviderTest {

    private SecretKey key;
    private JwtAuthTokenProvider jwtAuthTokenProvider;
    private JwtAuthTokenProvider invalidTokenJwtAuthTokenProvider;

    @BeforeEach
    void setUp() {
        final String testSecretkey = "testSecretkeytestSecretkeytestSecretkeytestSecretkey";
        key = Keys.hmacShaKeyFor(testSecretkey.getBytes(StandardCharsets.UTF_8));
        final String invalidSecretKey = "invalidSecretKeyinvalidSecretKeyinvalidSecretKeyinvalidSecretKey";
        jwtAuthTokenProvider = new JwtAuthTokenProvider(testSecretkey);
        invalidTokenJwtAuthTokenProvider = new JwtAuthTokenProvider(invalidSecretKey);
    }

    @Test
    void 액세스_토큰을_생성한다() {
        final String accessToken
                = jwtAuthTokenProvider.createAccessToken(1L, Role.MEMBER, ZonedDateTime.now().plusSeconds(2));

        final Claims payload = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
        assertAll(
                () -> assertThat(payload.get("memberId", Long.class)).isEqualTo(1L),
                () -> assertThat(payload.get("role", String.class)).isEqualTo(Role.MEMBER.name())
        );
    }

    @Test
    void 생성된_액세스_토큰을_멤버_정보로_파싱한다() {
        final String accessToken
                = jwtAuthTokenProvider.createAccessToken(1L, Role.MEMBER, ZonedDateTime.now().plusSeconds(2));

        final LoginMemberInfo loginMember = jwtAuthTokenProvider.getLoginMember(accessToken);

        assertAll(
                () -> assertThat(loginMember.getMemberId()).isEqualTo(1L),
                () -> assertThat(loginMember.getRole()).isSameAs(Role.MEMBER)
        );
    }

    @Test
    void 다른_키로_생성된_액세스_토큰을_파싱하면_에러가_발생한다() {
        final String invalidAccessToken = invalidTokenJwtAuthTokenProvider.createAccessToken(
                1L,
                Role.MEMBER,
                ZonedDateTime.now().plusSeconds(2)
        );

        assertThatThrownBy(() -> jwtAuthTokenProvider.getLoginMember(invalidAccessToken))
                .isInstanceOf(JwtInvalidSecretKeyException.class);
    }

    @Test
    void 만료된_액세스_토큰을_파싱하면_에러가_발생한다() {
        final String expiredAccessToken
                = jwtAuthTokenProvider.createAccessToken(1L, Role.MEMBER, ZonedDateTime.now().minusNanos(1L));

        assertThatThrownBy(() -> jwtAuthTokenProvider.getLoginMember(expiredAccessToken))
                .isInstanceOf(JwtInvalidExpiredException.class);
    }

    @Test
    void 리프레시_토큰을_생성한다() {
        final String refreshToken = jwtAuthTokenProvider.createRefreshToken(1L, ZonedDateTime.now().plusSeconds(2));

        final Claims payload = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();
        assertThat(payload.get("memberId", Long.class)).isEqualTo(1L);
    }

    @Test
    void 액세스_토큰으로_파싱이_안되는_문자열이_들어오면_에러가_발생한다() {
        assertThatThrownBy(() -> jwtAuthTokenProvider.getLoginMember("파싱이안되는문자열"))
                .isInstanceOf(JwtInvalidFormException.class);
    }

    @Test
    void 리프레시_토큰을_파싱하여_멤버_id_를_얻는다() {
        final String refreshToken = jwtAuthTokenProvider.createRefreshToken(1L, ZonedDateTime.now().plusSeconds(2));

        final Long memberId = jwtAuthTokenProvider.getMemberIdByRefreshToken(refreshToken);

        assertThat(memberId).isEqualTo(1L);
    }

    @Test
    void 다른_키로_생성된_리프레시_토큰을_파싱하면_에러가_발생한다() {
        final String invalidRefreshToken
                = invalidTokenJwtAuthTokenProvider.createRefreshToken(1L, ZonedDateTime.now().plusSeconds(2));

        assertThatThrownBy(() -> jwtAuthTokenProvider.getMemberIdByRefreshToken(invalidRefreshToken))
                .isInstanceOf(JwtInvalidSecretKeyException.class);
    }

    @Test
    void 만료된_리프레시_토큰을_파싱하면_에러가_발생한다() {
        final String expiredRefreshToken
                = jwtAuthTokenProvider.createRefreshToken(1L, ZonedDateTime.now().minusNanos(1L));

        assertThatThrownBy(() -> jwtAuthTokenProvider.getMemberIdByRefreshToken(expiredRefreshToken))
                .isInstanceOf(JwtInvalidExpiredException.class);
    }

    @Test
    void 리프레시_토큰으로_파싱이_안되는_문자열이_들어오면_에러가_발생한다() {
        assertThatThrownBy(() -> jwtAuthTokenProvider.getMemberIdByRefreshToken("파싱이안되는문자열"))
                .isInstanceOf(JwtInvalidFormException.class);
    }
}
