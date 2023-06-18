package org.prography.kagongsillok.auth.domain;

import java.time.ZonedDateTime;
import org.prography.kagongsillok.member.domain.Role;

public interface AuthTokenProvider {

    String createAccessToken(Long memberId, Role role, long accessTokenExpireMilliseconds);

    String createRefreshToken(Long memberId, ZonedDateTime expireDateTime);

    LoginMember getLoginUser(String token);

    String getRefreshTokenMemberId(String token);
}
