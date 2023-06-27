package org.prography.kagongsillok.review.application.exception;

import org.prography.kagongsillok.common.exception.NotFoundException;

public class NotFoundReviewException extends NotFoundException {

    public NotFoundReviewException(final Long reviewId) {
        super(String.format("리뷰가 존재하지 않습니다. reviewId = %d", reviewId));
    }
}
