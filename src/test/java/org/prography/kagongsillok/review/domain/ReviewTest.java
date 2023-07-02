package org.prography.kagongsillok.review.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.ReviewTag.domain.ReviewReviewTags;
import org.prography.kagongsillok.ReviewTag.domain.ReviewTagMapping;
import org.prography.kagongsillok.tag.domain.Tag;

public class ReviewTest {

    @Test
    void 태그가_없는_리뷰를_생성한다() {
        final Review review = Review
                .builder()
                .rating(4)
                .content("test review")
                .imageIds(List.of(1L, 2L, 3L))
                .tags(new ArrayList<>())
                .memberId(1L)
                .placeId(1L)
                .build();

        assertAll(
                () -> assertThat(review.getRating()).isEqualTo(4),
                () -> assertThat(review.getContent()).isEqualTo("test review"),
                () -> assertThat(review.getImageIds()).isEqualTo(List.of(1L, 2L, 3L)),
                () -> assertThat(review.getTags().getReviewTagMappings()).isEqualTo(new ArrayList<>()),
                () -> assertThat(review.getMemberId()).isEqualTo(1L)
        );
    }

    @Test
    void 태그가_있는_리뷰를_생성한다() {
        final Tag tag1 = new Tag("#tag1", "test tag1");
        final Tag tag2 = new Tag("#tag2", "test tag2");
        final Review review = Review
                .builder()
                .rating(4)
                .content("test review")
                .imageIds(List.of(1L, 2L, 3L))
                .tags(new ArrayList<>())
                .memberId(1L)
                .placeId(1L)
                .build();
        new ReviewTagMapping(review, tag1);
        new ReviewTagMapping(review, tag2);

        assertAll(
                () -> assertThat(review.getRating()).isEqualTo(4),
                () -> assertThat(review.getContent()).isEqualTo("test review"),
                () -> assertThat(review.getImageIds()).isEqualTo(List.of(1L, 2L, 3L)),
                () -> assertThat(review.getTags().getReviewTagMappings().size()).isEqualTo(2),
                () -> assertThat(review.getTags().getReviewTagMappings()).extracting("tag")
                        .extracting("tagName").containsAll(List.of("#tag1", "#tag2")),
                () -> assertThat(review.getMemberId()).isEqualTo(1L)
        );
    }
}
