package org.prography.kagongsillok.review.application.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewImagesInfoDto {

    private int totalImageCount;
    private List<ReviewImageDto> reviewImageDtos;

    private ReviewImagesInfoDto(final int totalImageCount, final List<ReviewImageDto> reviewImageDtos) {
        this.totalImageCount = totalImageCount;
        this.reviewImageDtos = reviewImageDtos;
    }

    public static ReviewImagesInfoDto of(final int totalImageCount, final List<ReviewImageDto> reviewImageDtos) {
        return new ReviewImagesInfoDto(totalImageCount, reviewImageDtos);
    }
}
