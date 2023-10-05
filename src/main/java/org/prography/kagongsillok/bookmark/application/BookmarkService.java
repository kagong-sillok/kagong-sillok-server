package org.prography.kagongsillok.bookmark.application;

import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.bookmark.domain.BookmarkToggleSwitch;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkToggleSwitch bookmarkToggleSwitch;

    public void toggle(final Long placeId, final Long memberId) {
        bookmarkToggleSwitch.toggle(placeId, memberId);
    }
}
