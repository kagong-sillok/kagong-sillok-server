package org.prography.kagongsillok.auth.domain;

import java.time.ZonedDateTime;
import org.prography.kagongsillok.auth.domain.dto.LoginMemberInfo;
import org.prography.kagongsillok.member.domain.Role;

public interface AuthTokenProvider {

    String createAccessToken(Long memberId, Role role, ZonedDateTime accessTokenExpire);

    String createRefreshToken(Long memberId, ZonedDateTime expireDateTime);

    LoginMemberInfo getLoginMember(String token);

    Long getMemberIdByRefreshToken(String token);
}
