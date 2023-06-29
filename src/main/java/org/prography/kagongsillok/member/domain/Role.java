package org.prography.kagongsillok.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    MEMBER("일반 회원"),
    ADMIN("관리자"),
    ;

    private final String description;
}
