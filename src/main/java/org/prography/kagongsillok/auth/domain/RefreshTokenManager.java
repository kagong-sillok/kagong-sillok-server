package org.prography.kagongsillok.auth.domain;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.auth.domain.entity.RefreshToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenManager {

    private static final int MAX_REFRESH_TOKEN_COUNT_PER_MEMBER = 3;

    private final AuthTokenProvider authTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${security.jwt.token.refresh-token-expire-length}")
    private long refreshTokenExpireMilliseconds;

    public RefreshToken create(final Long memberId) {
        final ZonedDateTime expireDateTime = ZonedDateTime.now()
                .plus(refreshTokenExpireMilliseconds, ChronoUnit.MILLIS);

        final String refreshTokenValue = authTokenProvider.createRefreshToken(memberId, expireDateTime);
        final RefreshToken refreshToken = new RefreshToken(refreshTokenValue, memberId, expireDateTime);

        final List<RefreshToken> refreshTokens = refreshTokenRepository.findByMemberId(memberId);
        if (refreshTokens.size() >= MAX_REFRESH_TOKEN_COUNT_PER_MEMBER) {
            refreshTokenRepository.removeOldRefreshToken(memberId);
        }
        refreshTokenRepository.save(refreshToken);

        return refreshToken;
    }
}
