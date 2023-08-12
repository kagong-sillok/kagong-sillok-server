package org.prography.kagongsillok.review.application.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewImageListDto {

    private int totalImageCount;
    private List<ReviewImageDto> reviewImageDtos;

    private ReviewImageListDto(final int totalImageCount, final List<ReviewImageDto> reviewImageDtos) {
        this.totalImageCount = totalImageCount;
        this.reviewImageDtos = reviewImageDtos;
    }

    public static ReviewImageListDto of(final int totalImageCount, final List<ReviewImageDto> reviewImageDtos) {
        return new ReviewImageListDto(totalImageCount, reviewImageDtos);
    }
}
