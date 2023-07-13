package org.prography.kagongsillok.record.domain.exception;

import org.prography.kagongsillok.common.exception.InvalidParamException;

public final class InvalidStudyDateException extends InvalidParamException {

    public InvalidStudyDateException(final String date) {
        super(String.format("입력한 Date 값이 올바르지 않습니다. Date = %s", date));
    }
}
