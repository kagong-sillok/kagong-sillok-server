package org.prography.kagongsillok.review.ui.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.review.application.dto.ReviewImageDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewImageResponse {

    private String imageUrl;
    private String memberName;
    private String memberProfileUrl;

    private ReviewImageResponse(final ReviewImageDto reviewImageDto) {
        this.imageUrl = reviewImageDto.getImageUrl();
        this.memberName = reviewImageDto.getMemberName();
        this.memberProfileUrl = reviewImageDto.getMemberProfileUrl();
    }

    public static ReviewImageResponse from(final ReviewImageDto reviewImageDto) {
        return new ReviewImageResponse(reviewImageDto);
    }
}
