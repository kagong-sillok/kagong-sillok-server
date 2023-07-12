package org.prography.kagongsillok.review.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.review.domain.ReviewTag;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewTagCreateCommand {

    private String tagName;
    private String tagContent;

    public ReviewTagCreateCommand(final String tagName, final String tagContent) {
        this.tagName = tagName;
        this.tagContent = tagContent;
    }

    public ReviewTag toEntity() {
        return new ReviewTag(tagName, tagContent);
    }
}
