package org.prography.kagongsillok.review.application.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.review.domain.Review;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewUpdateCommand {

    private Long id;
    private Long memberId;
    private int rating;
    private String content;
    private List<Long> imageIds;
    private List<Long> tagIds;

    @Builder
    public ReviewUpdateCommand(
            final Long id,
            final Long memberId,
            final int rating,
            final String content,
            final List<Long> imageIds,
            final List<Long> tagIds
    ) {
        this.id = id;
        this.memberId = memberId;
        this.rating = rating;
        this.content = content;
        this.imageIds = imageIds;
        this.tagIds = tagIds;
    }

    public Review toEntity() {
        return Review.builder()
                .memberId(memberId)
                .rating(rating)
                .content(content)
                .imageIds(imageIds)
                .tagIds(tagIds)
                .build();
    }
}
