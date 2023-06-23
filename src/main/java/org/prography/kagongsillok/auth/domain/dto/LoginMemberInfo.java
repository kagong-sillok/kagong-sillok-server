package org.prography.kagongsillok.auth.domain.dto;

import lombok.Getter;
import org.prography.kagongsillok.member.domain.Role;

@Getter
public class LoginMemberInfo {

    private final Long memberId;
    private final Role role;

    public LoginMemberInfo(final Long memberId, final String role) {
        this.memberId = memberId;
        this.role = Role.valueOf(role);
    }
}
