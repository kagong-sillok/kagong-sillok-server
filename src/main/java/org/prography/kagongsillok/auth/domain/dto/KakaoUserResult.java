package org.prography.kagongsillok.auth.domain.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KakaoUserResult {

    private final Long kakaoId;
    private final String email;
    private final String nickname;
}
