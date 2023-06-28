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
    private List<String> tags;
    private List<String> imageUrls;

    @Builder
    public ReviewCreateRequest(
            final Long memberId,
            final int rating,
            final String content,
            final List<String> tags,
            final List<String> imageUrls
    ) {
        this.memberId = memberId;
        this.rating = rating;
        this.content = content;
        this.tags = tags;
        this.imageUrls = imageUrls;
    }

    public ReviewCreateCommand toCommand() {
        return ReviewCreateCommand
                .builder()
                .memberId(memberId)
                .rating(rating)
                .content(content)
                .tags(tags)
                .imageUrls(imageUrls)
                .build();
    }

}
