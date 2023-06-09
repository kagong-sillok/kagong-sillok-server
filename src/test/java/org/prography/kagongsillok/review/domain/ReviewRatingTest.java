package org.prography.kagongsillok.review.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.prography.kagongsillok.review.domain.exception.InvalidRatingBoundException;
import org.prography.kagongsillok.review.domain.vo.ReviewRating;

public class ReviewRatingTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 3})
    void 평점을_생성한다(final int rawRating) {
        final ReviewRating rating = ReviewRating.from(rawRating);

        assertThat(rating.getValue()).isEqualTo(rawRating);
    }

    @Test
    void 평점이_1보다_작으면_예외가_발생한다() {
        assertThatThrownBy(() -> ReviewRating.from(0))
                .isInstanceOf(InvalidRatingBoundException.class);
    }

    @Test
    void 평점이_5보다_크면_예외가_발생한다() {
        assertThatThrownBy(() -> ReviewRating.from(6))
                .isInstanceOf(InvalidRatingBoundException.class);
    }
}
