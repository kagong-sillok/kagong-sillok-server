package org.prography.kagongsillok.auth.mock;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import org.prography.kagongsillok.auth.infrastructure.KakaoAuthApiFeignClient;
import org.prography.kagongsillok.auth.infrastructure.dto.KakaoAccessTokenFeignResponse;

public class MockKakaoAuthApiFeignClient implements KakaoAuthApiFeignClient {

    @Override
    public KakaoAccessTokenFeignResponse getAccessToken(
            final String grantType,
            final String clientId,
            final String redirectUri,
            final String authorizationCode,
            final String clientSecret) {
        try {
            return getKakaoAccessTokenFeignResponse();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private KakaoAccessTokenFeignResponse getKakaoAccessTokenFeignResponse() throws Exception {
        final Class<KakaoAccessTokenFeignResponse> clazz = KakaoAccessTokenFeignResponse.class;
        final Constructor<KakaoAccessTokenFeignResponse> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);

        final KakaoAccessTokenFeignResponse kakaoAccessTokenFeignResponse = constructor.newInstance();
        final Field accessTokenField = clazz.getDeclaredField("accessToken");
        accessTokenField.setAccessible(true);

        accessTokenField.set(kakaoAccessTokenFeignResponse, "testKakaoAccessToken");
        return kakaoAccessTokenFeignResponse;
    }
}
