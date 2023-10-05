package org.prography.kagongsillok.bookmark.domain;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.place.domain.Place;
import org.prography.kagongsillok.place.domain.PlaceRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class BookmarkToggleSwitch {

    private final BookmarkRepository bookmarkRepository;
    private final PlaceRepository placeRepository;

    @Transactional
    public void toggle(final Long placeId, final Long memberId) {
        final Optional<Bookmark> bookmark
                = bookmarkRepository.findByPlaceIdAndMemberIdAndIsDeletedFalse(placeId, memberId);

        bookmark.ifPresentOrElse(this::unbookmark, () -> bookmark(placeId, memberId));
    }

    private void unbookmark(final Bookmark bookmark) {
        bookmark.delete();
        placeRepository.findById(bookmark.getPlaceId())
                .ifPresent(Place::decreaseBookmarkCount);
    }

    private void bookmark(final Long placeId, final Long memberId) {
        final Bookmark bookmark = new Bookmark(placeId, memberId);
        bookmarkRepository.save(bookmark);
    }
}
