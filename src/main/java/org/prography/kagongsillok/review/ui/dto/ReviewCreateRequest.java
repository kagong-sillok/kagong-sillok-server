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
    private int rating;
    private String content;
    private List<Long> imageIds;
    private List<Long> tagIds;

    @Builder
    public ReviewCreateRequest(
            final Long memberId,
            final int rating,
            final String content,
            final List<Long> imageIds,
            final List<Long> tagIds
    ) {
        this.memberId = memberId;
        this.rating = rating;
        this.content = content;
        this.imageIds = imageIds;
        this.tagIds = tagIds;
    }

    public ReviewCreateCommand toCommand() {
        return ReviewCreateCommand
                .builder()
                .memberId(memberId)
                .rating(rating)
                .content(content)
                .tagIds(tagIds)
                .imageIds(imageIds)
                .build();
    }

}
