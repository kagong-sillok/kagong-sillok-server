package org.prography.kagongsillok;

import org.prography.kagongsillok.auth.domain.RefreshTokenRepository;
import org.prography.kagongsillok.auth.infrastructure.KakaoAuthApiFeignClient;
import org.prography.kagongsillok.auth.infrastructure.KakaoResourceApiFeignClient;
import org.prography.kagongsillok.auth.mock.MockKakaoAuthApiFeignClient;
import org.prography.kagongsillok.auth.mock.MockKakaoResourceApiFeignClient;
import org.prography.kagongsillok.auth.mock.MockRefreshTokenRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class MockTestConfig {

    @Bean
    @Primary
    public KakaoAuthApiFeignClient kakaoAuthApiFeignClient() {
        return new MockKakaoAuthApiFeignClient();
    }

    @Bean
    @Primary
    public KakaoResourceApiFeignClient kakaoResourceApiFeignClient() {
        return new MockKakaoResourceApiFeignClient();
    }

    @Bean
    @Primary
    public RefreshTokenRepository refreshTokenRepository() {
        return new MockRefreshTokenRepository();
    }
}
