package org.prography.kagongsillok.review.domain.exception;

import org.prography.kagongsillok.common.exception.InvalidParamException;

public final class InvalidNumberOfImagesException extends InvalidParamException {

    public InvalidNumberOfImagesException(final int maxNumberOfImage, final int numberOfImages) {
        super(String.format("리뷰 사진은 %d개를 초과할 수 없습니다. Number of images = %d", maxNumberOfImage, numberOfImages));
    }
}
