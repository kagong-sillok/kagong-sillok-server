package org.prography.kagongsillok.record.domain.exception;

import java.time.ZonedDateTime;
import org.prography.kagongsillok.common.exception.InvalidParamException;

public final class InvalidStudyMonthException extends InvalidParamException {

    public InvalidStudyMonthException(final int month) {
        super(String.format("입력한 Month 범위가 올바르지 않습니다. Month = %d", month));
    }
}
