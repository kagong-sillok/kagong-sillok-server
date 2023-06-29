package org.prography.kagongsillok.auth.application.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.member.domain.Member;
import org.prography.kagongsillok.member.domain.Role;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalJoinCommand {

    private String loginId;
    private String password;
    private String nickname;
    private String email;
    private String role;

    @Builder
    public LocalJoinCommand(
            final String loginId,
            final String password,
            final String nickname,
            final String email,
            final String role
    ) {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
    }

    public Member toMemberEntity() {
        return new Member(nickname, email, Role.valueOf(role));
    }
}
