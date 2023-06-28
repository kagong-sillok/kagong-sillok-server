package org.prography.kagongsillok.review.domain.exception;

import org.prography.kagongsillok.common.exception.InvalidParamException;

public final class InvalidRatingBoundException extends InvalidParamException {

    public InvalidRatingBoundException(final int rating) {
        super(String.format("입력한 별점 값이 올바르지 않습니다. Rating = %d", rating));
    }
}
