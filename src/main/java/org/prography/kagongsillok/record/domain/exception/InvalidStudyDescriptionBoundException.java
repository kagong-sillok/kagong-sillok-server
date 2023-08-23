package org.prography.kagongsillok.record.domain.exception;

import org.prography.kagongsillok.common.exception.InvalidParamException;

public final class InvalidStudyDescriptionBoundException extends InvalidParamException {

    public InvalidStudyDescriptionBoundException(final String description) {
        super(String.format("입력한 공부내용 길이가 올바르지 않습니다. description length = %d", description.length()));
    }
}
