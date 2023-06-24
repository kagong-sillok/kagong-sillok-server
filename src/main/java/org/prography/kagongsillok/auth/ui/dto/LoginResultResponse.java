package org.prography.kagongsillok.auth.ui.dto;

import java.time.ZonedDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.auth.application.dto.LoginResultDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginResultResponse {

    public String accessToken;
    public String refreshToken;
    public ZonedDateTime accessTokenExpireDateTime;
    public ZonedDateTime refreshTokenExpireDateTime;

    @Builder
    public LoginResultResponse(
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

    public static LoginResultResponse from(final LoginResultDto loginResultDto) {
        return LoginResultResponse.builder()
                .accessToken(loginResultDto.getAccessToken())
                .refreshToken(loginResultDto.getRefreshToken())
                .accessTokenExpireDateTime(loginResultDto.getAccessTokenExpireDateTime())
                .refreshTokenExpireDateTime(loginResultDto.getRefreshTokenExpireDateTime())
                .build();
    }
}
