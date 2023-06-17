package org.prography.kagongsillok.auth.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthType {

    LOCAL("로컬 회원"),
    KAKAO("카카오 회원"),
    ;

    private final String description;
}
