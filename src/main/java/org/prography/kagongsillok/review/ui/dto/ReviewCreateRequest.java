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

    @Builder
    public ReviewCreateRequest(
            final Long userId,
            final int rating,
            final String content,
            final List<String> tags,
            final List<String> imageUrls
    ) {
        this.userId = userId;
        this.rating = rating;
        this.content = content;
        this.tags = tags;
        this.imageUrls = imageUrls;
    }

    private Long userId;
    private int rating;
    private String content;
    private List<String> tags;
    private List<String> imageUrls;

    public ReviewCreateCommand toCommand() {
        return ReviewCreateCommand
                .builder()
                .userId(userId)
                .rating(rating)
                .content(content)
                .tags(tags)
                .imageUrls(imageUrls)
                .build();
    }

}
