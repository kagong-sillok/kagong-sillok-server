package org.prography.kagongsillok.auth.domain;

import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.auth.application.dto.LoginResultDto;
import org.prography.kagongsillok.auth.domain.dto.AccessTokenCreateInfo;
import org.prography.kagongsillok.auth.domain.entity.RefreshToken;
import org.prography.kagongsillok.member.application.exception.NotFoundMemberException;
import org.prography.kagongsillok.member.domain.Member;
import org.prography.kagongsillok.member.domain.MemberRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginManager {

    private final AccessTokenManager accessTokenManager;
    private final RefreshTokenManager refreshTokenManager;
    private final MemberRepository memberRepository;

    public LoginResultDto loginMember(final Member member) {
        final AccessTokenCreateInfo accessTokenCreateInfo = accessTokenManager.create(member);
        final RefreshToken refreshToken = refreshTokenManager.create(member.getId());

        return LoginResultDto.builder()
                .accessToken(accessTokenCreateInfo.getAccessToken())
                .refreshToken(refreshToken.getValue())
                .accessTokenExpireDateTime(accessTokenCreateInfo.getExpire())
                .refreshTokenExpireDateTime(refreshToken.getExpire())
                .build();
    }

    public LoginResultDto refresh(final String refreshTokenValue) {
        final RefreshToken newRefreshToken = refreshTokenManager.rotation(refreshTokenValue);
        final Member member = memberRepository.findById(newRefreshToken.getMemberId())
                .orElseThrow(() -> new NotFoundMemberException(newRefreshToken.getMemberId()));
        final AccessTokenCreateInfo newAccessTokenResult = accessTokenManager.create(member);

        return LoginResultDto.builder()
                .accessToken(newAccessTokenResult.getAccessToken())
                .refreshToken(newRefreshToken.getValue())
                .accessTokenExpireDateTime(newAccessTokenResult.getExpire())
                .refreshTokenExpireDateTime(newRefreshToken.getExpire())
                .build();
    }
}
