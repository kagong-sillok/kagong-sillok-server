package org.prography.kagongsillok.auth.mock;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import org.prography.kagongsillok.auth.infrastructure.KakaoResourceApiFeignClient;
import org.prography.kagongsillok.auth.infrastructure.dto.KakaoUserFeignResponse;
import org.prography.kagongsillok.auth.infrastructure.dto.KakaoUserFeignResponse.KakaoAccountFeignResponse;
import org.prography.kagongsillok.auth.infrastructure.dto.KakaoUserFeignResponse.ProfileFeignResponse;

public class MockKakaoResourceApiFeignClient implements KakaoResourceApiFeignClient {

    @Override
    public KakaoUserFeignResponse getKakaoUser(
            final String authorizationHeader,
            final String contentTypeHeader,
            final Boolean secureResource,
            final String propertyKeys
    ) {
        try {
            final ProfileFeignResponse profileFeignResponse = getProfileFeignResponse();
            final KakaoAccountFeignResponse kakaoAccountFeignResponse
                    = getKakaoAccountFeignResponse(profileFeignResponse);
            return getKakaoUserFeignResponse(kakaoAccountFeignResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ProfileFeignResponse getProfileFeignResponse() throws Exception {
        final Class<ProfileFeignResponse> clazz = ProfileFeignResponse.class;
        final Constructor<ProfileFeignResponse> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        final ProfileFeignResponse instance = constructor.newInstance();

        final Field nicknameField = clazz.getDeclaredField("nickname");
        nicknameField.setAccessible(true);
        final Field profileImageUrlField = clazz.getDeclaredField("profileImageUrl");
        profileImageUrlField.setAccessible(true);

        nicknameField.set(instance, "testUserNickname");
        profileImageUrlField.set(instance, "testProfileImageUrl");
        return instance;
    }

    private KakaoAccountFeignResponse getKakaoAccountFeignResponse(final ProfileFeignResponse profile)
            throws Exception {
        final Class<KakaoAccountFeignResponse> clazz = KakaoAccountFeignResponse.class;
        final Constructor<KakaoAccountFeignResponse> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        final KakaoAccountFeignResponse instance = constructor.newInstance();

        final Field emailField = clazz.getDeclaredField("email");
        emailField.setAccessible(true);
        final Field profileField = clazz.getDeclaredField("profile");
        profileField.setAccessible(true);

        emailField.set(instance, "test@test.com");
        profileField.set(instance, profile);
        return instance;
    }

    private KakaoUserFeignResponse getKakaoUserFeignResponse(final KakaoAccountFeignResponse kakaoAccount)
            throws Exception {
        final Class<KakaoUserFeignResponse> clazz = KakaoUserFeignResponse.class;
        final Constructor<KakaoUserFeignResponse> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        final KakaoUserFeignResponse instance = constructor.newInstance();

        final Field idField = clazz.getDeclaredField("id");
        idField.setAccessible(true);
        final Field kakaoAccountField = clazz.getDeclaredField("kakaoAccount");
        kakaoAccountField.setAccessible(true);

        idField.set(instance, 99_999_999L);
        kakaoAccountField.set(instance, kakaoAccount);
        return instance;
    }
}
