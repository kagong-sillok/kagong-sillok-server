package org.prography.kagongsillok.review.ui.dto;

import java.time.ZonedDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.review.application.dto.ReviewDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewResponse {

    private Long id;
    private int rating;
    private String content;
    private List<Long> imageIds;
    private List<Long> tagIds;
    private Long memberId;
    private Long placeId;
    private String userNickName;
    private ZonedDateTime writtenAt;

    @Builder
    public ReviewResponse(
            final Long id,
            final int rating,
            final String content,
            final List<Long> imageIds,
            final List<Long> tagIds,
            final Long memberId,
            final Long placeId,
            final String userNickName,
            final ZonedDateTime writtenAt
    ) {
        this.id = id;
        this.rating = rating;
        this.content = content;
        this.imageIds = imageIds;
        this.tagIds = tagIds;
        this.memberId = memberId;
        this.placeId = placeId;
        this.userNickName = userNickName;
        this.writtenAt = writtenAt;
    }

    public static ReviewResponse from(ReviewDto reviewDto) {
        return ReviewResponse
                .builder()
                .id(reviewDto.getId())
                .rating(reviewDto.getRating())
                .content(reviewDto.getContent())
                .memberId(reviewDto.getMemberId())
                .placeId(reviewDto.getPlaceId())
                .imageIds(reviewDto.getImageIds())
                .tagIds(reviewDto.getTagIds())
                .userNickName("임시 닉네임")
                .writtenAt(reviewDto.getWrittenAt())
                .build();
    }
}
