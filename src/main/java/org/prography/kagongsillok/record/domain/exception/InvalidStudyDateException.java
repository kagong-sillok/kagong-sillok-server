package org.prography.kagongsillok.record.domain.exception;

import java.time.ZonedDateTime;
import org.prography.kagongsillok.common.exception.InvalidParamException;

public final class InvalidStudyDateException extends InvalidParamException {

    public InvalidStudyDateException(final int date) {
        super(String.format("입력한 Date 범위가 올바르지 않습니다. Date = %d", date));
    }
}
