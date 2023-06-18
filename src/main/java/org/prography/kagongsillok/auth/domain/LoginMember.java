package org.prography.kagongsillok.auth.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.prography.kagongsillok.member.domain.Role;

@Getter
@EqualsAndHashCode
@ToString
public class LoginMember {

    private final Long memberId;
    private final Role role;

    public LoginMember(final Long memberId, final String role) {
        this.memberId = memberId;
        this.role = Role.valueOf(role);
    }
}
