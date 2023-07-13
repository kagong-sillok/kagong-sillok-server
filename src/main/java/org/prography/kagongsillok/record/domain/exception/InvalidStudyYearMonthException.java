package org.prography.kagongsillok.record.domain.exception;

import org.prography.kagongsillok.common.exception.InvalidParamException;

public final class InvalidStudyYearMonthException extends InvalidParamException {

    public InvalidStudyYearMonthException(final String yearMonth) {
        super(String.format("입력한 YearMonth 값이 올바르지 않습니다. YearMonth = %s", yearMonth));
    }
}
