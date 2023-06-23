package org.prography.kagongsillok.auth.domain.dto;

import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccessTokenCreateInfo {

    private final String accessToken;
    private final ZonedDateTime expire;
}
