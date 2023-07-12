package org.prography.kagongsillok.review.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ReviewTest {

    @Test
    void 태그가_없는_리뷰를_생성한다() {
        final Review review = Review.builder()
                .rating(4)
                .content("test review")
                .imageIds(List.of(1L, 2L, 3L))
                .tagMappings(new ArrayList<>())
                .memberId(1L)
                .placeId(1L)
                .build();

        assertAll(
                () -> assertThat(review.getRating()).isEqualTo(4),
                () -> assertThat(review.getContent()).isEqualTo("test review"),
                () -> assertThat(review.getImageIds()).isEqualTo(List.of(1L, 2L, 3L)),
                () -> assertThat(review.getTagMappings().getValues()).isEqualTo(new ArrayList<>()),
                () -> assertThat(review.getMemberId()).isEqualTo(1L)
        );
    }

    @Test
    void 태그가_있는_리뷰를_생성한다() {
        final ReviewTag reviewTag1 = new ReviewTag("#tag1", "test tag1");
        final ReviewTag reviewTag2 = new ReviewTag("#tag2", "test tag2");
        final Review review = Review.builder()
                .rating(4)
                .content("test review")
                .imageIds(List.of(1L, 2L, 3L))
                .tagMappings(List.of(new ReviewTagMapping(reviewTag1), new ReviewTagMapping(reviewTag2)))
                .memberId(1L)
                .placeId(1L)
                .build();

        assertAll(
                () -> assertThat(review.getRating()).isEqualTo(4),
                () -> assertThat(review.getContent()).isEqualTo("test review"),
                () -> assertThat(review.getImageIds()).isEqualTo(List.of(1L, 2L, 3L)),
                () -> assertThat(review.getTagMappings().getValues().size()).isEqualTo(2),
                () -> assertThat(review.getTagMappings().getValues()).extracting("reviewTag")
                        .extracting("tagName").containsAll(List.of("#tag1", "#tag2")),
                () -> assertThat(review.getMemberId()).isEqualTo(1L)
        );
    }
}
