package org.prography.kagongsillok.record.domain.exception;

import java.time.ZonedDateTime;
import org.prography.kagongsillok.common.exception.InvalidParamException;

public final class InvalidStudyDateException extends InvalidParamException {

    public InvalidStudyDateException(final int year, final int month, final int day) {
        super(String.format("입력한 공부 날짜가 올바르지 않습니다. year = %d, month = %d, day = %d", year, month, day));
    }
}
