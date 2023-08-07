package org.prography.kagongsillok.review.ui.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.review.application.dto.ReviewImagesInfoDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewImagesInfoResponse {

    private int totalImageCount;
    private List<ReviewImageResponse> reviewImages;

    private ReviewImagesInfoResponse(final ReviewImagesInfoDto reviewImagesInfoDto) {
        this.totalImageCount = reviewImagesInfoDto.getTotalImageCount();
        this.reviewImages = CustomListUtils.mapTo(
                reviewImagesInfoDto.getReviewImageDtos(),
                ReviewImageResponse::from
        );
    }

    public static ReviewImagesInfoResponse from(final ReviewImagesInfoDto reviewImagesInfoDto) {
        return new ReviewImagesInfoResponse(reviewImagesInfoDto);
    }
}
