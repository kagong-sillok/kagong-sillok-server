package org.prography.kagongsillok.review.ui.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.review.application.dto.ReviewTagCreateCommand;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewTagCreateRequest {

    private String tagName;
    private String tagContent;

    public ReviewTagCreateRequest(final String tagName, final String tagContent) {
        this.tagName = tagName;
        this.tagContent = tagContent;
    }

    public ReviewTagCreateCommand toCommand() {
        return new ReviewTagCreateCommand(tagName, tagContent);
    }
}
