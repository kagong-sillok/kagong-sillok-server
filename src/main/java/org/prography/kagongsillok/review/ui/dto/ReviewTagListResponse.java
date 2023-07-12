package org.prography.kagongsillok.review.ui.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.review.application.dto.ReviewTagDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewTagListResponse {

    public List<ReviewTagResponse> tags;

    public static ReviewTagListResponse from(final List<ReviewTagDto> reviewTagDtos) {
        return new ReviewTagListResponse(CustomListUtils.mapTo(reviewTagDtos, ReviewTagResponse::from));
    }
}
