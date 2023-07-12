package org.prography.kagongsillok.review.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.review.domain.ReviewTag;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewTagDto {

    private Long id;
    private String tagName;
    private String tagContent;

    public ReviewTagDto(final Long id, final String tagName, final String tagContent) {
        this.id = id;
        this.tagName = tagName;
        this.tagContent = tagContent;
    }

    public static ReviewTagDto from(final ReviewTag reviewTag) {
        return new ReviewTagDto(reviewTag.getId(), reviewTag.getTagName(), reviewTag.getTagContent());
    }
}
