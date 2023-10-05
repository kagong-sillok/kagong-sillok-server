package org.prography.kagongsillok.bookmark.ui.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookmarkToggleRequest {

    private Long placeId;

    public BookmarkToggleRequest(final Long placeId) {
        this.placeId = placeId;
    }
}
