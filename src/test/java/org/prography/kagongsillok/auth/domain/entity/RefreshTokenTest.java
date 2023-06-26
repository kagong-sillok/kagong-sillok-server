package org.prography.kagongsillok.auth.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

class RefreshTokenTest {

    @Test
    void 리프레시_토큰을_생성한다() {
        final ZonedDateTime expire = ZonedDateTime.now().plusSeconds(1L);

        final RefreshToken refreshToken = new RefreshToken("refreshTokenValue", 1L, expire);

        assertAll(
                () -> assertThat(refreshToken.getMemberId()).isEqualTo(1L),
                () -> assertThat(refreshToken.getId()).isNotNull(),
                () -> assertThat(refreshToken.getValue()).isEqualTo("refreshTokenValue"),
                () -> assertThat(refreshToken.getExpire()).isEqualTo(expire)
        );
    }

    @Test
    void 리프레시_토큰이_만료되었는지_확인한다_notExpired() {
        final ZonedDateTime expire = ZonedDateTime.now().plusSeconds(1L);
        final RefreshToken refreshToken = new RefreshToken("refreshTokenValue", 1L, expire);

        assertThat(refreshToken.isNotExpired()).isTrue();
    }

    @Test
    void 리프레시_토큰이_만료되었는지_확인한다_expired() {
        final ZonedDateTime expire = ZonedDateTime.now().minusSeconds(1L);
        final RefreshToken refreshToken = new RefreshToken("refreshTokenValue", 1L, expire);

        assertThat(refreshToken.isNotExpired()).isFalse();
    }
}
