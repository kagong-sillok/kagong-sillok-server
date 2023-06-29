package org.prography.kagongsillok.auth.mock;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.prography.kagongsillok.auth.domain.RefreshTokenRepository;
import org.prography.kagongsillok.auth.domain.entity.RefreshToken;

public class MockRefreshTokenRepository implements RefreshTokenRepository {

    private static final Map<Long, Deque<RefreshToken>> repository = new ConcurrentHashMap<>();

    @Override
    public RefreshToken save(final RefreshToken refreshToken) {
        final Deque<RefreshToken> refreshTokens = repository.get(refreshToken.getMemberId());
        saveRefreshToken(refreshToken, refreshTokens);
        return refreshToken;
    }

    private void saveRefreshToken(final RefreshToken refreshToken, final Deque<RefreshToken> refreshTokens) {
        if (refreshTokens == null) {
            firstSave(refreshToken);
            return;
        }
        refreshTokens.addFirst(refreshToken);
    }

    private void firstSave(final RefreshToken refreshToken) {
        final Deque<RefreshToken> newRefreshTokenByMember = new LinkedList<>();
        newRefreshTokenByMember.addFirst(refreshToken);
        repository.put(refreshToken.getMemberId(), newRefreshTokenByMember);
    }

    @Override
    public List<RefreshToken> findByMemberId(final Long memberId) {
        final Deque<RefreshToken> refreshTokens = repository.get(memberId);
        if (refreshTokens == null) {
            return List.of();
        }
        return new ArrayList<>(refreshTokens);
    }

    @Override
    public void removeOldRefreshToken(final Long memberId) {
        final Deque<RefreshToken> refreshTokens = repository.get(memberId);
        if (refreshTokens == null) {
            return;
        }
        refreshTokens.removeLast();
    }

    @Override
    public void removeRefreshToken(final Long memberId, final String deletedRefreshTokenId) {
        final Deque<RefreshToken> refreshTokens = repository.get(memberId);
        if (refreshTokens == null) {
            return;
        }
        refreshTokens.removeIf(it -> it.getId().equals(deletedRefreshTokenId));
    }
}
