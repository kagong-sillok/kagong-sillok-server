package org.prography.kagongsillok.auth.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.MockTestConfig;
import org.prography.kagongsillok.auth.domain.entity.RefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
@Import(MockTestConfig.class)
class RefreshTokenManagerTest {

    @Autowired
    private RefreshTokenManager refreshTokenManager;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @BeforeEach
    void setUp() {
        final Set<String> keys = redisTemplate.keys("*");
        if (keys != null) {
            redisTemplate.delete(keys);
        }
    }

    @AfterEach
    void tearDown() {
        final Set<String> keys = redisTemplate.keys("*");
        if (keys != null) {
            redisTemplate.delete(keys);
        }
    }

    @Test
    void 리프레시_토큰을_생성한다() {
        final RefreshToken refreshToken = refreshTokenManager.create(1L);

        assertAll(
                () -> assertThat(refreshToken.getId()).isNotNull(),
                () -> assertThat(refreshToken.getMemberId()).isEqualTo(1L),
                () -> assertThat(refreshToken.getExpire()).isAfter(ZonedDateTime.now())
        );
    }

    @Test
    void 리프레시_토큰을_한_멤버당_3개_이상_생성하면_오래된_리프레시_토큰을_삭제한다() {
        final RefreshToken refreshToken1 = refreshTokenManager.create(1L);
        final RefreshToken refreshToken2 = refreshTokenManager.create(1L);
        final RefreshToken refreshToken3 = refreshTokenManager.create(1L);

        final RefreshToken refreshToken4 = refreshTokenManager.create(1L);

        final List<RefreshToken> refreshTokens = refreshTokenRepository.findByMemberId(1L);
        assertThat(refreshTokens).extracting("id")
                .containsOnly(refreshToken2.getId(), refreshToken3.getId(), refreshToken4.getId());
    }

    @Test
    void 기존의_리프레시_토큰을_삭제하고_새_리프레시_토큰으로_바꿔준다() {
        final RefreshToken oldRefreshToken = refreshTokenManager.create(1L);

        final RefreshToken newRefreshToken = refreshTokenManager.rotation(oldRefreshToken.getValue());

        final List<RefreshToken> refreshTokens = refreshTokenRepository.findByMemberId(1L);
        final boolean oldRefreshTokenDeleted = refreshTokens
                .stream()
                .filter(it -> it.getId().equals(oldRefreshToken.getId()))
                .findAny()
                .isEmpty();
        assertAll(
                () -> assertThat(newRefreshToken.getId()).isNotNull(),
                () -> assertThat(newRefreshToken.getId()).isNotEqualTo(oldRefreshToken.getId()),
                () -> assertThat(newRefreshToken.getMemberId()).isEqualTo(oldRefreshToken.getMemberId()),
                () -> assertThat(newRefreshToken.getExpire()).isAfter(oldRefreshToken.getExpire()),
                () -> assertThat(oldRefreshTokenDeleted).isTrue()
        );
    }
}
