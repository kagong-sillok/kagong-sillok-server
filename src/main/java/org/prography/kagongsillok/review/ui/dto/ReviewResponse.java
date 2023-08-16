package org.prography.kagongsillok.review.ui.dto;

import java.time.ZonedDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.image.domain.Image;
import org.prography.kagongsillok.review.application.dto.ReviewDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewResponse {

    private Long id;
    private int rating;
    private String content;
    private List<Image> images;
    private List<Long> tagIds;
    private Long memberId;
    private Long placeId;
    private String memberNickName;
    private ZonedDateTime writtenAt;

    @Builder
    public ReviewResponse(
            final Long id,
            final int rating,
            final String content,
            final List<Image> images,
            final List<Long> tagIds,
            final Long memberId,
            final Long placeId,
            final String memberNickName,
            final ZonedDateTime writtenAt
    ) {
        this.id = id;
        this.rating = rating;
        this.content = content;
        this.images = images;
        this.tagIds = tagIds;
        this.memberId = memberId;
        this.placeId = placeId;
        this.memberNickName = memberNickName;
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
                .images(reviewDto.getImages())
                .tagIds(reviewDto.getTagIds())
                .memberNickName(reviewDto.getMemberNickName())
                .writtenAt(reviewDto.getWrittenAt())
                .build();
    }
}
