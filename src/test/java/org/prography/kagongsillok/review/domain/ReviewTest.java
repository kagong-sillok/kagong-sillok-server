package org.prography.kagongsillok.review.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.prography.kagongsillok.place.domain.BusinessHour;
import org.prography.kagongsillok.place.domain.DayOfWeek;
import org.prography.kagongsillok.place.domain.Link;
import org.prography.kagongsillok.place.domain.LinkType;
import org.prography.kagongsillok.place.domain.Location;
import org.prography.kagongsillok.place.domain.Place;

public class ReviewTest {

    @Test
    void 리뷰를_생성한다() {
        final Review review = Review
                .builder()
                .rating(4)
                .content("test review")
                .tags(Tags.of(Set.of("#tag1", "#tag2")))
                .images(Images.of(Set.of("image1", "image2")))
                .memberId(1L)
                .build();

        assertAll(
                () -> assertThat(review.getRating()).isEqualTo(4),
                () -> assertThat(review.getContent()).isEqualTo("test review"),
                () -> assertThat(review.getTags().getTags()).containsAll(Set.of("#tag1", "#tag2")),
                () -> assertThat(review.getImages().getImages()).containsAll(Set.of("image1", "image2")),
                () -> assertThat(review.getMemberId()).isEqualTo(1L)
        );
    }
}
