package org.prography.kagongsillok.auth.domain;

import org.prography.kagongsillok.auth.domain.dto.KakaoUserResult;

public interface KakaoApiCaller {

    String getAccessToken(
            String authorizationCode,
            String kakaoOAuthRedirectUri,
            String kakaoOAuthClientId,
            String kakaoOAuthClientSecret
    );

    KakaoUserResult getKakaoUser(String kakaoAccessToken);
}
