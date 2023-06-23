package org.prography.kagongsillok.auth.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.auth.domain.dto.KakaoUserInfo;
import org.prography.kagongsillok.auth.mock.MockKakaoAuthApiFeignClient;
import org.prography.kagongsillok.auth.mock.MockKakaoResourceApiFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@SpringBootTest
class FeignKakaoApiCallerTest {

    @Autowired
    private FeignKakaoApiCaller feignKakaoApiCaller;

    @Test
    void 카카오_엑세스_토큰을_요청한다() {
        final String accessToken = feignKakaoApiCaller.getAccessToken(
                "authorizationCode",
                "kakaoOAuthRedirectUri",
                "clientId",
                "clientSecret"
        );

        assertThat(accessToken).isEqualTo("testKakaoAccessToken");
    }

    @Test
    void 엑세스_토큰으로_유저_정보를_요청한다() {
        final KakaoUserInfo kakaoUser = feignKakaoApiCaller.getKakaoUser("accessToken");

        assertAll(
                () -> assertThat(kakaoUser.getKakaoId()).isEqualTo(99_999_999L),
                () -> assertThat(kakaoUser.getEmail()).isEqualTo("test@test.com"),
                () -> assertThat(kakaoUser.getNickname()).isEqualTo("testUserNickname"),
                () -> assertThat(kakaoUser.getProfileImageUrl()).isEqualTo("testProfileImageUrl")
        );
    }

    @TestConfiguration
    public static class MockApiClientConfiguration {

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
    }
}
