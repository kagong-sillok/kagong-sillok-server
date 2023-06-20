package org.prography.kagongsillok.auth.ui.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.auth.application.dto.KakaoJoinCommand;
import org.prography.kagongsillok.member.domain.Role;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KakaoJoinRequest {

    private String authorizationCode;
    private String redirectUri;

    public KakaoJoinCommand toCommand() {
        return new KakaoJoinCommand(authorizationCode, Role.MEMBER.name(), redirectUri);
    }
}
