package org.prography.kagongsillok.review.application.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.review.domain.Images;
import org.prography.kagongsillok.review.domain.Review;
import org.prography.kagongsillok.review.domain.Tags;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewUpdateCommand {

    private Long id;
    private Long memberId;
    private int rating;
    private String content;
    private List<String> tags;
    private List<String> imageUrls;

    @Builder
    public ReviewUpdateCommand(
            final Long id,
            final Long memberId,
            final int rating,
            final String content,
            final List<String> tags,
            final List<String> imageUrls) {
        this.id = id;
        this.memberId = memberId;
        this.rating = rating;
        this.content = content;
        this.tags = tags;
        this.imageUrls = imageUrls;
    }

    public Review toEntity() {
        return Review.builder()
                .memberId(memberId)
                .rating(rating)
                .content(content)
                .tags(Tags.of(tags.stream().collect(Collectors.toSet())))
                .images(Images.of(imageUrls.stream().collect(Collectors.toSet())))
                .build();
    }
}
