package org.prography.kagongsillok.auth.domain;

import lombok.Getter;

@Getter
public class RefreshToken {

    private String tokenId;
    private Long memberId;
}
