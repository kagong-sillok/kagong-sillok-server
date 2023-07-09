package org.prography.kagongsillok.review.application.dto;

import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.review.domain.Review;
import org.prography.kagongsillok.review.domain.ReviewTag;
import org.prography.kagongsillok.review.domain.ReviewTagMapping;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewUpdateCommand {

    private Long memberId;
    private Long placeId;
    private int rating;
    private String content;
    private List<Long> imageIds;
    private List<Long> reviewTagIds;

    @Builder
    public ReviewUpdateCommand(
            final Long memberId,
            final Long placeId,
            final int rating,
            final String content,
            final List<Long> imageIds,
            final List<Long> reviewTagIds
    ) {
        this.memberId = memberId;
        this.placeId = placeId;
        this.rating = rating;
        this.content = content;
        this.imageIds = imageIds;
        this.reviewTagIds = reviewTagIds;
    }

    public Review toEntity(final Map<Long, ReviewTag> reviewTags) {
        return Review.builder()
                .memberId(memberId)
                .placeId(placeId)
                .rating(rating)
                .content(content)
                .imageIds(imageIds)
                .tagMappings(
                        CustomListUtils.mapToExcludeNull(
                                reviewTagIds,
                                (reviewTagId) -> new ReviewTagMapping(reviewTags.get(reviewTagId))
                        )
                )
                .build();
    }
}
