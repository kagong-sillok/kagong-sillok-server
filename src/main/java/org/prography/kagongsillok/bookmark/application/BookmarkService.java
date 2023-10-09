package org.prography.kagongsillok.bookmark.application;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.bookmark.domain.BookmarkToggleSwitch;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    public static final String BOOKMARK_LOCK_FORMAT = "lockBookmarkToggle::placeId=%d";
    private final BookmarkToggleSwitch bookmarkToggleSwitch;
    private final RedissonClient redissonClient;

    public void toggle(final Long placeId, final Long memberId) {
        final RLock lock = redissonClient.getLock(String.format(BOOKMARK_LOCK_FORMAT, placeId));

        try {
            final boolean available = lock.tryLock(15, 1, TimeUnit.SECONDS);
            if (!available) {
                throw new RuntimeException("락이 오랜 시간 획득되지 않습니다. 데드락이 발생했을 수 있습니다.");
            }
            bookmarkToggleSwitch.toggle(placeId, memberId);
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
