package org.prography.kagongsillok.auth.domain;

import java.util.List;
import org.prography.kagongsillok.auth.domain.entity.RefreshToken;

public interface RefreshTokenRepository {

    RefreshToken save(RefreshToken refreshToken);
    List<RefreshToken> findByMemberId(Long memberId);

    void removeOldRefreshToken(Long memberId);

    void removeRefreshToken(Long memberId, String deletedRefreshTokenId);
}
