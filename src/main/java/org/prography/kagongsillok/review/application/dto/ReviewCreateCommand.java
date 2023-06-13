package org.prography.kagongsillok.review.application.dto;

import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.common.utils.CustomListUtils;
import org.prography.kagongsillok.image.domain.Image;
import org.prography.kagongsillok.review.domain.Images;
import org.prography.kagongsillok.review.domain.Review;
import org.prography.kagongsillok.review.domain.Tags;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewCreateCommand {

    private Long userId;
    private int rating;
    private String content;
    private List<String> tags;
    private List<String> imageUrls;

    @Builder
    public ReviewCreateCommand(
            final Long userId,
            final int rating,
            final String content,
            final List<String> tags,
            final List<String> imageUrls) {
        this.userId = userId;
        this.rating = rating;
        this.content = content;
        this.tags = tags;
        this.imageUrls = imageUrls;
    }

    public Review toEntity() {
        return Review.builder()
                .userId(userId)
                .rating(rating)
                .content(content)
                .tags(Tags.of(tags))
                .images(Images.of(imageUrls))
                .build();
    }
}
