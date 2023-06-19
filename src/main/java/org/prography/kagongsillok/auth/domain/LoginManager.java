package org.prography.kagongsillok.auth.domain;

import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.auth.application.dto.LoginResultDto;
import org.prography.kagongsillok.auth.domain.dto.AccessTokenCreateResult;
import org.prography.kagongsillok.auth.domain.entity.RefreshToken;
import org.prography.kagongsillok.member.domain.Member;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginManager {

    private final AccessTokenManager accessTokenManager;
    private final RefreshTokenManager refreshTokenManager;

    public LoginResultDto loginMember(final Member member) {
        final AccessTokenCreateResult accessTokenCreateResult = accessTokenManager.create(member);
        final RefreshToken refreshToken = refreshTokenManager.create(member.getId());

        return LoginResultDto.builder()
                .accessToken(accessTokenCreateResult.getAccessToken())
                .refreshToken(refreshToken.getValue())
                .accessTokenExpireDateTime(accessTokenCreateResult.getExpire())
                .refreshTokenExpireDateTime(refreshToken.getExpire())
                .build();
    }
}
