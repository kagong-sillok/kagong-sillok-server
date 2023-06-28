package org.prography.kagongsillok.review.domain.exception;

import org.prography.kagongsillok.common.exception.InvalidParamException;

public final class InvalidContentLengthException extends InvalidParamException {

    public InvalidContentLengthException(final String content) {
        super(String.format("올바르지 않은 길이의 리뷰를 입력하였습니다. Content Length = %d", content.length()));
    }
}
