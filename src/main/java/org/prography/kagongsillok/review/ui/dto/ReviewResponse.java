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

    @Builder
    public ReviewResponse(
            final Long id,
            final int rating,
            final String content,
            final List<String> images,
            final List<String> tags,
            final Long memberId,
            final String userNickName,
            final String createdAt,
            final String updatedAt
    ) {
        this.id = id;
        this.rating = rating;
        this.content = content;
        this.imageUrls = images;
        this.tags = tags;
        this.memberId = memberId;
        this.userNickName = userNickName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private Long id;
    private int rating;
    private String content;
    private List<String> imageUrls;
    private List<String> tags;
    private Long memberId;
    private String userNickName;
    private String createdAt;
    private String updatedAt;

    public static ReviewResponse from(ReviewDto reviewDto) {
        return ReviewResponse
                .builder()
                .id(reviewDto.getId())
                .rating(reviewDto.getRating())
                .content(reviewDto.getContent())
                .memberId(reviewDto.getMemberId())
                .tags(reviewDto.getTags())
                .images(reviewDto.getImageUrls())
                .userNickName("임시 닉네임")
                .createdAt(reviewDto.getCreatedAt())
                .updatedAt(reviewDto.getUpdatedAt())
                .build();
    }
}
