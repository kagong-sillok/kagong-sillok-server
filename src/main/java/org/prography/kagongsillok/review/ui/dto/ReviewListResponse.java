package org.prography.kagongsillok.review.ui.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.review.application.dto.ReviewDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewListResponse {

    private List<ReviewResponse> reviews;

    public static ReviewListResponse of(final List<ReviewDto> reviewDtos) {
        return new ReviewListResponse(CustomListUtils.mapTo(reviewDtos, ReviewResponse::from));
    }
}
