package org.prography.kagongsillok.common.resolver.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.member.domain.Role;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginMemberDto {
    private Long memberId;
    private Role role;

    public LoginMemberDto(final Long memberId, final Role role) {
        this.memberId = memberId;
        this.role = role;
    }
}
