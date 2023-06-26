package org.prography.kagongsillok.member.domain.exception;

import org.prography.kagongsillok.common.exception.InvalidParamException;

public final class InvalidEmailException extends InvalidParamException {

    public InvalidEmailException(final String value) {
        super(String.format("이메일 형식이 아닙니다. value = %s", value));
    }
}
