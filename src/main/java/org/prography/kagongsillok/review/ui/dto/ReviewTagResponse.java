package org.prography.kagongsillok.review.ui.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.review.application.dto.ReviewTagDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewTagResponse {

    private Long id;
    private String tagName;
    private String tagContent;

    public ReviewTagResponse(final Long id, final String tagName, final String tagContent) {
        this.id = id;
        this.tagName = tagName;
        this.tagContent = tagContent;
    }

    public static ReviewTagResponse from(final ReviewTagDto reviewTagDto) {
        return new ReviewTagResponse(reviewTagDto.getId(), reviewTagDto.getTagName(), reviewTagDto.getTagContent());
    }
}
