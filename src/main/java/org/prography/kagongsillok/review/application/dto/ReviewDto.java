package org.prography.kagongsillok.review.application.dto;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.review.domain.Review;
import org.prography.kagongsillok.review.domain.ReviewTagMapping;
import org.prography.kagongsillok.review.domain.ReviewTagMappings;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewDto {

    private Long id;
    private Long memberId;
    private Long placeId;
    private String memberNickName;
    private int rating;
    private String content;
    private List<Long> imageIds;
    private List<Long> tagIds;
    private ZonedDateTime writtenAt;

    @Builder
    public ReviewDto(
            final Long id,
            final Long memberId,
            final Long placeId,
            final String memberNickName,
            final int rating,
            final String content,
            final List<Long> imageIds,
            final List<Long> tagIds,
            final ZonedDateTime writtenAt
    ) {
        this.id = id;
        this.memberId = memberId;
        this.placeId = placeId;
        this.memberNickName = memberNickName;
        this.rating = rating;
        this.content = content;
        this.imageIds = imageIds;
        this.tagIds = tagIds;
        this.writtenAt = writtenAt;
    }

    public static ReviewDto from(Review review) {
        return ReviewDto
                .builder()
                .id(review.getId())
                .memberId(review.getMemberId())
                .placeId(review.getPlaceId())
                .memberNickName(review.getMemberNickName())
                .rating(review.getRating())
                .content(review.getContent())
                .imageIds(review.getImageIds())
                .tagIds(getTagIds(review.getTagMappings()))
                .writtenAt(review.getWrittenAt())
                .build();
    }

    private static List<Long> getTagIds(ReviewTagMappings reviewTags) {
        List<ReviewTagMapping> reviewTagMappings = reviewTags.getValues();
        List<Long> tagIds = new ArrayList<>();

        for (ReviewTagMapping reviewTagMapping : reviewTagMappings) {
            tagIds.add(reviewTagMapping.getReviewTag().getId());
        }

        return tagIds;
    }
}
