package org.prography.kagongsillok.record.domain.exception;

import org.prography.kagongsillok.common.exception.InvalidParamException;

public final class InvalidDurationBoundException extends InvalidParamException {

    public InvalidDurationBoundException(final String duration) {
        super(String.format("입력한 공부 시간 값이 올바르지 않습니다. Duration = %s", duration));
    }
}
