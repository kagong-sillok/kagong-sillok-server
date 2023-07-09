package org.prography.kagongsillok.review.application.exception;

import org.prography.kagongsillok.common.exception.NotFoundException;

public final class NotFoundReviewTagException extends NotFoundException {

    public NotFoundReviewTagException(final Long reviewTagId) {
        super(String.format("존재하지 않는 리뷰태그 Id가 있습니다. reviewTagId = %d", reviewTagId));
    }
}
