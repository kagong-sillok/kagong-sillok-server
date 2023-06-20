package org.prography.kagongsillok.auth.ui.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KakaoLoginRequest {

    private String authorizationCode;
    private String redirectUri;
}
