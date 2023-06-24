package org.prography.kagongsillok.auth.ui.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.auth.application.dto.LocalJoinCommand;
import org.prography.kagongsillok.member.domain.Role;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalJoinRequest {

    private String loginId;
    private String password;
    private String nickname;
    private String email;

    @Builder
    public LocalJoinRequest(final String loginId, final String password, final String nickname, final String email) {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }

    public LocalJoinCommand toCommand() {
        return LocalJoinCommand.builder()
                .loginId(loginId)
                .password(password)
                .nickname(nickname)
                .email(email)
                .role(Role.MEMBER.name())
                .build();
    }
}
