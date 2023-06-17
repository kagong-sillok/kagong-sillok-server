package org.prography.kagongsillok.auth.application.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginResultDto {

    public String accessToken;
    public String refreshToken;
    public Integer accessTokenExpired;
    public Integer refreshTokenExpired;

    @Builder
    public LoginResultDto(
            final String accessToken,
            final String refreshToken,
            final Integer accessTokenExpired,
            final Integer refreshTokenExpired
    ) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpired = accessTokenExpired;
        this.refreshTokenExpired = refreshTokenExpired;
    }
}
