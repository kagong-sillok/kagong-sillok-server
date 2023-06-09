package org.prography.kagongsillok.review.domain.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prography.kagongsillok.review.domain.exception.InvalidRatingBoundException;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewRating {

    private static final int MAX_RATING = 5;
    private static final int MIN_RATING = 1;

    @Column(name = "rating")
    private int value;

    private ReviewRating(final int value) {
        this.value = value;
    }

    public static ReviewRating from(final int value) {
        validateValue(value);
        return new ReviewRating(value);
    }

    private static void validateValue(final int value) {
        if (isUnderRating(value) || isOverRating(value)) {
            throw new InvalidRatingBoundException(value);
        }
    }

    private static boolean isUnderRating(final int value) {
        return value < MIN_RATING;
    }

    private static boolean isOverRating(final int value) {
        return value > MAX_RATING;
    }
}
