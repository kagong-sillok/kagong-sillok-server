package org.prography.kagongsillok.auth.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.auth.domain.entity.RefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class RefreshTokenRedisListRepositoryTest {

    @Autowired
    private RefreshTokenRedisListRepository refreshTokenRedisListRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @BeforeEach
    void setUp() {
        final Set<String> keys = redisTemplate.keys("member::refreshToken::*");
        if (keys != null) {
            redisTemplate.delete(keys);
        }
    }

    @AfterEach
    void tearDown() {
        final Set<String> keys = redisTemplate.keys("member::refreshToken::*");
        if (keys != null) {
            redisTemplate.delete(keys);
        }
    }

    @Test
    void 리프레시_토큰을_memberId별로_list자료형으로_저장한다() {
        final ZonedDateTime refreshTokenExpire = ZonedDateTime.now().plusDays(30);
        final RefreshToken refreshToken1 = new RefreshToken("refreshToken1", 1L, refreshTokenExpire);
        final RefreshToken refreshToken2 = new RefreshToken("refreshToken2", 1L, refreshTokenExpire);

        refreshTokenRedisListRepository.save(refreshToken1);
        refreshTokenRedisListRepository.save(refreshToken2);

        final List<RefreshToken> refreshTokens = refreshTokenRedisListRepository.findByMemberId(1L);
        assertAll(
                () -> assertThat(refreshTokens).hasSize(2),
                () -> assertThat(refreshTokens).extracting("id")
                        .containsOnly(refreshToken1.getId(), refreshToken2.getId()),
                () -> assertThat(refreshTokens).extracting("memberId")
                        .containsOnly(1L),
                () -> assertThat(refreshTokens).extracting("value")
                        .containsOnly(refreshToken1.getValue(), refreshToken2.getValue())
        );
    }

    @Test
    void 멤버_아이디로_리프레시_토큰_목록을_조회한다() {
        final ZonedDateTime refreshTokenExpire = ZonedDateTime.now().plusDays(30);
        final RefreshToken refreshToken1 = new RefreshToken("refreshToken1", 1L, refreshTokenExpire);
        final RefreshToken refreshToken2 = new RefreshToken("refreshToken2", 1L, refreshTokenExpire);
        final RefreshToken refreshToken3 = new RefreshToken("refreshToken3", 2L, refreshTokenExpire);
        final RefreshToken refreshToken4 = new RefreshToken("refreshToken4", 2L, refreshTokenExpire);
        final RefreshToken refreshToken5 = new RefreshToken("refreshToken5", 3L, refreshTokenExpire);
        refreshTokenRedisListRepository.save(refreshToken1);
        refreshTokenRedisListRepository.save(refreshToken2);
        refreshTokenRedisListRepository.save(refreshToken3);
        refreshTokenRedisListRepository.save(refreshToken4);
        refreshTokenRedisListRepository.save(refreshToken5);

        final List<RefreshToken> member2RefreshTokens = refreshTokenRedisListRepository.findByMemberId(2L);

        assertAll(
                () -> assertThat(member2RefreshTokens).hasSize(2),
                () -> assertThat(member2RefreshTokens).extracting("id")
                        .containsOnly(refreshToken3.getId(), refreshToken4.getId())
        );
    }

    @Test
    void 리프레시_토큰을_삭제한다() {
        final ZonedDateTime refreshTokenExpire = ZonedDateTime.now().plusDays(30);
        final RefreshToken refreshToken1 = new RefreshToken("refreshToken1", 1L, refreshTokenExpire);
        final RefreshToken refreshToken2 = new RefreshToken("refreshToken2", 1L, refreshTokenExpire);
        final RefreshToken refreshToken3 = new RefreshToken("refreshToken3", 1L, refreshTokenExpire);
        refreshTokenRedisListRepository.save(refreshToken1);
        refreshTokenRedisListRepository.save(refreshToken2);
        refreshTokenRedisListRepository.save(refreshToken3);

        refreshTokenRedisListRepository.removeRefreshToken(1L, refreshToken2);

        final List<RefreshToken> refreshTokens = refreshTokenRedisListRepository.findByMemberId(1L);
        assertAll(
                () -> assertThat(refreshTokens).hasSize(2),
                () -> assertThat(refreshTokens).extracting("id")
                        .containsOnly(refreshToken1.getId(), refreshToken3.getId()),
                () -> assertThat(refreshTokens).extracting("value")
                        .containsOnly(refreshToken1.getValue(), refreshToken3.getValue())
        );
    }
}
