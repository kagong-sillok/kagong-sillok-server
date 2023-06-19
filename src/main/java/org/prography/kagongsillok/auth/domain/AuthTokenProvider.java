package org.prography.kagongsillok.auth.domain;

import java.time.ZonedDateTime;
import org.prography.kagongsillok.auth.domain.dto.LoginMemberResult;
import org.prography.kagongsillok.member.domain.Role;

public interface AuthTokenProvider {

    String createAccessToken(Long memberId, Role role, long accessTokenExpireMilliseconds);

    String createRefreshToken(Long memberId, ZonedDateTime expireDateTime);

    LoginMemberResult getLoginMember(String token);

    String getRefreshTokenMemberId(String token);
}
