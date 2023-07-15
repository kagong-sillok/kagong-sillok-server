package org.prography.kagongsillok.record.domain.exception;

import java.time.ZonedDateTime;
import org.prography.kagongsillok.common.exception.InvalidParamException;

public final class InvalidStudyYearException extends InvalidParamException {

    public InvalidStudyYearException(final int year) {
        super(String.format("입력한 Year 범위가 올바르지 않습니다. Year = %d", year));
    }
}
