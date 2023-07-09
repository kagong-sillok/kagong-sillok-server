package org.prography.kagongsillok.review.ui.dto;


import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.review.application.dto.ReviewCreateCommand;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewCreateRequest {

    private Long memberId;
    private Long placeId;
    private int rating;
    private String content;
    private List<Long> imageIds;
    private List<Long> reviewTagIds;

    @Builder
    public ReviewCreateRequest(
            final Long memberId,
            final Long placeId,
            final int rating,
            final String content,
            final List<Long> imageIds,
            final List<Long> reviewTagIds
    ) {
        this.memberId = memberId;
        this.placeId = placeId;
        this.rating = rating;
        this.content = content;
        this.imageIds = imageIds;
        this.reviewTagIds = reviewTagIds;
    }

    public ReviewCreateCommand toCommand() {
        return ReviewCreateCommand
                .builder()
                .memberId(memberId)
                .placeId(placeId)
                .rating(rating)
                .content(content)
                .reviewTagIds(reviewTagIds)
                .imageIds(imageIds)
                .build();
    }
}
