package org.prography.kagongsillok.review.application.dto;

import java.util.List;
import java.util.stream.Collectors;
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

    private Long memberId;
    private int rating;
    private String content;
    private List<String> tags;
    private List<String> imageUrls;

    @Builder
    public ReviewCreateCommand(
            final Long memberId,
            final int rating,
            final String content,
            final List<String> tags,
            final List<String> imageUrls) {
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
