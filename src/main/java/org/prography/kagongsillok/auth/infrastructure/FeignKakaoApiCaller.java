package org.prography.kagongsillok.auth.infrastructure;

import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.auth.domain.KakaoApiCaller;
import org.prography.kagongsillok.auth.domain.dto.KakaoUserResult;
import org.prography.kagongsillok.auth.infrastructure.dto.KakaoUserFeignResponse;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeignKakaoApiCaller implements KakaoApiCaller {

    private final KakaoAuthApiFeignClient authApiFeignClient;
    private final KakaoResourceApiFeignClient resourceApiFeignClient;

    @Override
    public String getAccessToken(
            final String authorizationCode,
            final String kakaoOAuthRedirectUri,
            final String kakaoOAuthClientId,
            final String kakaoOAuthClientSecret
    ) {
        return authApiFeignClient.getAccessToken(
                "authorization_code",
                kakaoOAuthClientId,
                kakaoOAuthRedirectUri,
                authorizationCode,
                kakaoOAuthClientSecret
        ).getAccessToken();
    }

    @Override
    public KakaoUserResult getKakaoUser(final String kakaoAccessToken) {
        final String authorizationHeader = "Bearer " + kakaoAccessToken;
        final String contentType = "application/x-www-form-urlencoded;charset=utf-8";
        final String propertyKeys = "[\"kakao_account.profile\", \"kakao_account.name\", \"kakao_account.email\"]";
        final KakaoUserFeignResponse kakaoUser = resourceApiFeignClient.getKakaoUser(
                authorizationHeader,
                contentType,
                Boolean.TRUE,
                propertyKeys
        );

        return new KakaoUserResult(
                kakaoUser.getId(),
                kakaoUser.getEmail(),
                kakaoUser.getProfileImageUrl()
        );
    }
}
