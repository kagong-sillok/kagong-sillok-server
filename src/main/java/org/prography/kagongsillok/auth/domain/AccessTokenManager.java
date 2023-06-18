package org.prography.kagongsillok.auth.domain;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.auth.domain.dto.AccessTokenCreateResult;
import org.prography.kagongsillok.member.domain.Member;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccessTokenManager {

    private final AuthTokenProvider authTokenProvider;

    @Value("${security.jwt.token.access-token-expire-length}")
    private long accessTokenExpireMilliseconds;

    public AccessTokenCreateResult create(final Member member) {
        final ZonedDateTime accessTokenExpire = ZonedDateTime.now()
                .plus(accessTokenExpireMilliseconds, ChronoUnit.MILLIS);
        final String accessToken = authTokenProvider.createAccessToken(
                member.getId(),
                member.getRole(),
                accessTokenExpireMilliseconds
        );
        return new AccessTokenCreateResult(accessToken, accessTokenExpire);
    }
}
