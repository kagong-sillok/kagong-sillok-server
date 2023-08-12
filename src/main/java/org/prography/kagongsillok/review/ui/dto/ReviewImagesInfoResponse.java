package org.prography.kagongsillok.review.ui.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.review.application.dto.ReviewImageListDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewImagesInfoResponse {

    private int totalImageCount;
    private List<ReviewImageResponse> reviewImages;

    private ReviewImagesInfoResponse(final ReviewImageListDto reviewImageListDto) {
        this.totalImageCount = reviewImageListDto.getTotalImageCount();
        this.reviewImages = CustomListUtils.mapTo(
                reviewImageListDto.getReviewImageDtos(),
                ReviewImageResponse::from
        );
    }

    public static ReviewImagesInfoResponse from(final ReviewImageListDto reviewImageListDto) {
        return new ReviewImagesInfoResponse(reviewImageListDto);
    }
}
