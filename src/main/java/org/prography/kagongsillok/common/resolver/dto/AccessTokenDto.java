package org.prography.kagongsillok.common.resolver.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccessTokenDto {
    private String accessToken;

    public AccessTokenDto(final String accessToken) {
        this.accessToken = accessToken;
    }
}
