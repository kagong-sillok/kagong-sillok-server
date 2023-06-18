package org.prography.kagongsillok.auth.domain;

import java.util.List;

public interface RefreshTokenRepository {

    RefreshToken save(RefreshToken refreshToken);
    List<RefreshToken> findByMemberId(Long memberId);

    void removeOldRefreshToken(Long memberId);
}
