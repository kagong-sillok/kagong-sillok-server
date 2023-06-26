package org.prography.kagongsillok.auth.domain;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.prography.kagongsillok.auth.application.exception.NotLoginMemberException;
import org.prography.kagongsillok.auth.domain.entity.RefreshToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenManager {

    private static final int MAX_REFRESH_TOKEN_COUNT_PER_MEMBER = 3;

    private final AuthTokenProvider authTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final long refreshTokenExpireMilliseconds;

    public RefreshTokenManager(
            final AuthTokenProvider authTokenProvider,
            final RefreshTokenRepository refreshTokenRepository,
            @Value("${security.jwt.token.refresh-token-expire-length}") final long refreshTokenExpireMilliseconds
    ) {
        this.authTokenProvider = authTokenProvider;
        this.refreshTokenRepository = refreshTokenRepository;
        this.refreshTokenExpireMilliseconds = refreshTokenExpireMilliseconds;
    }

    public RefreshToken create(final Long memberId) {
        final ZonedDateTime expireDateTime = ZonedDateTime.now()
                .plus(refreshTokenExpireMilliseconds, ChronoUnit.MILLIS);
        final RefreshToken refreshToken = createRefreshToken(memberId, expireDateTime);

        final List<RefreshToken> refreshTokens = refreshTokenRepository.findByMemberId(memberId);
        if (isMaxLoginCountPerMember(refreshTokens)) {
            refreshTokenRepository.removeOldRefreshToken(memberId);
        }
        refreshTokenRepository.save(refreshToken);

        return refreshToken;
    }

    private boolean isMaxLoginCountPerMember(final List<RefreshToken> refreshTokens) {
        return refreshTokens.size() >= MAX_REFRESH_TOKEN_COUNT_PER_MEMBER;
    }

    public RefreshToken rotation(final String refreshTokenValue) {
        final Long memberId = authTokenProvider.getMemberIdByRefreshToken(refreshTokenValue);
        final RefreshToken refreshToken = findRefreshToken(refreshTokenValue, memberId);
        refreshTokenRepository.removeRefreshToken(memberId, refreshToken.getId());

        return createNewRefreshToken(memberId);
    }

    private RefreshToken findRefreshToken(final String refreshTokenValue, final Long memberId) {
        final List<RefreshToken> memberRefreshTokens = refreshTokenRepository.findByMemberId(memberId);
        return memberRefreshTokens.stream()
                .filter(it -> it.getValue().equals(refreshTokenValue))
                .findAny()
                .orElseThrow(() -> new NotLoginMemberException("무효화된 리프레시 토큰입니다. 다시 로그인 해주세요."));
    }

    private RefreshToken createNewRefreshToken(final Long memberId) {
        final ZonedDateTime expireDateTime = ZonedDateTime.now()
                .plus(refreshTokenExpireMilliseconds, ChronoUnit.MILLIS);
        final RefreshToken newRefreshToken = createRefreshToken(memberId, expireDateTime);

        return refreshTokenRepository.save(newRefreshToken);
    }

    private RefreshToken createRefreshToken(final Long memberId, final ZonedDateTime expireDateTime) {
        final String refreshTokenValue = authTokenProvider.createRefreshToken(memberId, expireDateTime);
        return new RefreshToken(refreshTokenValue, memberId, expireDateTime);
    }
}
