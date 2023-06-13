package org.prography.kagongsillok.review.application.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomTimeFormattingUtils;
import org.prography.kagongsillok.place.application.dto.PlaceDto.BusinessHourDto;
import org.prography.kagongsillok.place.application.dto.PlaceDto.LinkDto;
import org.prography.kagongsillok.review.domain.Review;
import org.prography.kagongsillok.review.domain.Tags;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewDto {

    @Builder
    public ReviewDto(
            final Long id,
            final Long userId,
            final int rating,
            final String content,
            final List<String> tags,
            final List<String> imageUrls,
            final String createdAt,
            final String updatedAt
    ) {
        this.id = id;
        this.userId = userId;
        this.rating = rating;
        this.content = content;
        this.tags = tags;
        this.imageUrls = imageUrls;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private Long id;
    private Long userId;
    private int rating;
    private String content;
    private List<String> tags;
    private List<String> imageUrls;
    private String createdAt;
    private String updatedAt;

    public static ReviewDto from(Review review) {
        return ReviewDto
                .builder()
                .id(review.getId())
                .userId(review.getUserId())
                .rating(review.getRating())
                .content(review.getContent())
                .tags(review.getTags().getTags())
                .imageUrls(review.getImages().getImages())
                .createdAt(
                        CustomTimeFormattingUtils
                                .LocalDateTimeToYearMonthDate(
                                        review.getCreatedAt()
                                )
                )
                .updatedAt(
                        CustomTimeFormattingUtils
                                .LocalDateTimeToYearMonthDate(
                                        review.getUpdatedAt()
                                )

                )
                .build();
    }
}
