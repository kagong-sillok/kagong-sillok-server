package org.prography.kagongsillok.review.application.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.review.domain.Review;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewCreateCommand {

    private Long memberId;
    private Long placeId;
    private int rating;
    private String content;
    private List<Long> imageIds;
    private List<Long> tagIds;

    @Builder
    public ReviewCreateCommand(
            final Long memberId,
            final Long placeId,
            final int rating,
            final String content,
            final List<Long> imageIds,
            final List<Long> tagIds) {
        this.memberId = memberId;
        this.placeId = placeId;
        this.rating = rating;
        this.content = content;
        this.imageIds = imageIds;
        this.tagIds = tagIds;
    }

    public Review toEntity() {
        return Review.builder()
                .memberId(memberId)
                .placeId(placeId)
                .rating(rating)
                .content(content)
                .imageIds(imageIds)
                .tags(new ArrayList<>())
                .build();
    }
}
