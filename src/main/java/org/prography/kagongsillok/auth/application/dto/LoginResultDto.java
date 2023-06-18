package org.prography.kagongsillok.auth.application.dto;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginResultDto {

    public String accessToken;
    public String refreshToken;
    public ZonedDateTime accessTokenExpireDateTime;
    public ZonedDateTime refreshTokenExpireDateTime;

    @Builder
    public LoginResultDto(
            final String accessToken,
            final String refreshToken,
            final ZonedDateTime accessTokenExpireDateTime,
            final ZonedDateTime refreshTokenExpireDateTime
    ) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpireDateTime = accessTokenExpireDateTime;
        this.refreshTokenExpireDateTime = refreshTokenExpireDateTime;
    }
}
