package org.prography.kagongsillok.auth.domain;

import org.prography.kagongsillok.auth.domain.dto.KakaoUserInfo;

public interface KakaoApiCaller {

    String getAccessToken(
            String authorizationCode,
            String kakaoOAuthRedirectUri,
            String kakaoOAuthClientId,
            String kakaoOAuthClientSecret
    );

    KakaoUserInfo getKakaoUser(String kakaoAccessToken);
}
