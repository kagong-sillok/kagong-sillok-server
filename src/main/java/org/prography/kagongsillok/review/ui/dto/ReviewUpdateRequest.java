package org.prography.kagongsillok.review.ui.dto;


import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.review.application.dto.ReviewCreateCommand;
import org.prography.kagongsillok.review.application.dto.ReviewUpdateCommand;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewUpdateRequest {

    @Builder
    public ReviewUpdateRequest(
            final Long id,
            final Long memberId,
            final int rating,
            final String content,
            final List<String> tags,
            final List<String> imageUrls
    ) {
        this.id = id;
        this.memberId = memberId;
        this.rating = rating;
        this.content = content;
        this.tags = tags;
        this.imageUrls = imageUrls;
    }

    private Long id;
    private Long memberId;
    private int rating;
    private String content;
    private List<String> tags;
    private List<String> imageUrls;

    public ReviewUpdateCommand toCommand() {
        return ReviewUpdateCommand
                .builder()
                .id(id)
                .memberId(memberId)
                .rating(rating)
                .content(content)
                .tags(tags)
                .imageUrls(imageUrls)
                .build();
    }

}
