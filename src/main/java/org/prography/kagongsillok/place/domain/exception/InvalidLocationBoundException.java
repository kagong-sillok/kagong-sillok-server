package org.prography.kagongsillok.place.domain.exception;

import org.prography.kagongsillok.common.exception.InvalidParamException;

public final class InvalidLocationBoundException extends InvalidParamException {

    public InvalidLocationBoundException(final Double latitude, final Double longitude) {
        super(String.format("입력된 위치의 범위가 올바르지 않습니다 latitude = %f, longitude = %f", latitude, longitude));
    }
}
