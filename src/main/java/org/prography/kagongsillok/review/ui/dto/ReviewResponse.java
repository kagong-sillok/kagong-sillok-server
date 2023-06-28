package org.prography.kagongsillok.review.ui.dto;

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
    private String userNickName;
    private String createdAt;
    private String updatedAt;

    @Builder
    public ReviewResponse(
            final Long id,
            final int rating,
            final String content,
            final List<Long> imageIds,
            final List<Long> tagIds,
            final Long memberId,
            final String userNickName,
            final String createdAt,
            final String updatedAt
    ) {
        this.id = id;
        this.rating = rating;
        this.content = content;
        this.imageIds = imageIds;
        this.tagIds = tagIds;
        this.memberId = memberId;
        this.userNickName = userNickName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ReviewResponse from(ReviewDto reviewDto) {
        return ReviewResponse
                .builder()
                .id(reviewDto.getId())
                .rating(reviewDto.getRating())
                .content(reviewDto.getContent())
                .memberId(reviewDto.getMemberId())
                .imageIds(reviewDto.getImageIds())
                .tagIds(reviewDto.getTagIds())
                .userNickName("임시 닉네임")
                .createdAt(reviewDto.getCreatedAt())
                .updatedAt(reviewDto.getUpdatedAt())
                .build();
    }
}
