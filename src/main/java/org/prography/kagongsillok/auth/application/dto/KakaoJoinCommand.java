package org.prography.kagongsillok.auth.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KakaoJoinCommand {

    private String authorizationCode;
    private String role;
    private String redirectUri;

    public KakaoJoinCommand(final String authorizationCode, final String role, final String redirectUri) {
        this.authorizationCode = authorizationCode;
        this.role = role;
        this.redirectUri = redirectUri;
    }
}
