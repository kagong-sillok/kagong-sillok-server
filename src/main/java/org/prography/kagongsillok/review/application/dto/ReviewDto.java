package org.prography.kagongsillok.review.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomTimeFormattingUtils;
import org.prography.kagongsillok.review.domain.Review;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewDto {

    private Long id;
    private Long memberId;
    private int rating;
    private String content;
    private List<Long> imageIds;
    private List<Long> tagIds;
    private String createdAt;
    private String updatedAt;

    @Builder
    public ReviewDto(
            final Long id,
            final Long memberId,
            final int rating,
            final String content,
            final List<Long> imageIds,
            final List<Long> tagIds,
            final String createdAt,
            final String updatedAt
    ) {
        this.id = id;
        this.memberId = memberId;
        this.rating = rating;
        this.content = content;
        this.imageIds = imageIds;
        this.tagIds = tagIds;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ReviewDto from(Review review) {
        return ReviewDto
                .builder()
                .id(review.getId())
                .memberId(review.getMemberId())
                .rating(review.getRating())
                .content(review.getContent())
                .imageIds(review.getImageIds())
                .tagIds(review.getTagIds())
                .createdAt(dateTimeToString(review.getCreatedAt()))
                .updatedAt(dateTimeToString(review.getUpdatedAt()))
                .build();
    }

    private static String dateTimeToString(LocalDateTime localDateTime) {
        return CustomTimeFormattingUtils
                .LocalDateTimeToYearMonthDate(localDateTime);
    }
}
