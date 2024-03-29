package org.prography.kagongsillok.review.ui.dto;

import java.time.ZonedDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.image.application.dto.ImageDto;
import org.prography.kagongsillok.image.domain.Image;
import org.prography.kagongsillok.review.application.dto.ReviewDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewResponse {

    private Long id;
    private int rating;
    private String content;
    private List<ImageDto> images;
    private List<Long> tagIds;
    private Long memberId;
    private Long placeId;
    private String placeName;
    private String memberNickName;
    private String memberProfileUrl;
    private ZonedDateTime writtenAt;

    @Builder
    public ReviewResponse(
            final Long id,
            final int rating,
            final String content,
            final List<ImageDto> images,
            final List<Long> tagIds,
            final Long memberId,
            final Long placeId,
            final String placeName,
            final String memberNickName,
            final String memberProfileUrl,
            final ZonedDateTime writtenAt
    ) {
        this.id = id;
        this.rating = rating;
        this.content = content;
        this.images = images;
        this.tagIds = tagIds;
        this.memberId = memberId;
        this.placeId = placeId;
        this.placeName = placeName;
        this.memberNickName = memberNickName;
        this.memberProfileUrl = memberProfileUrl;
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
                .placeName(reviewDto.getPlaceName())
                .images(reviewDto.getImages())
                .tagIds(reviewDto.getTagIds())
                .memberNickName(reviewDto.getMemberNickName())
                .memberProfileUrl(reviewDto.getMemberProfileUrl())
                .writtenAt(reviewDto.getWrittenAt())
                .build();
    }
}
