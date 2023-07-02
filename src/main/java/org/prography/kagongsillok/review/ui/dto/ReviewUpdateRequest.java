package org.prography.kagongsillok.review.ui.dto;


import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.review.application.dto.ReviewUpdateCommand;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewUpdateRequest {

    private Long id;
    private Long memberId;
    private Long placeId;
    private int rating;
    private String content;
    private List<Long> imageIds;
    private List<Long> tagIds;

    @Builder
    public ReviewUpdateRequest(
            final Long id,
            final Long memberId,
            final Long placeId,
            final int rating,
            final String content,
            final List<Long> imageIds,
            final List<Long> tagIds
    ) {
        this.id = id;
        this.memberId = memberId;
        this.placeId = placeId;
        this.rating = rating;
        this.content = content;
        this.imageIds = imageIds;
        this.tagIds = tagIds;
    }

    public ReviewUpdateCommand toCommand() {
        return ReviewUpdateCommand
                .builder()
                .id(id)
                .memberId(memberId)
                .placeId(placeId)
                .rating(rating)
                .content(content)
                .imageIds(imageIds)
                .tagIds(tagIds)
                .build();
    }

}
