package org.prography.kagongsillok.member.domain.exception;

import org.prography.kagongsillok.common.exception.InvalidParamException;

public final class TooLongNicknameException extends InvalidParamException {

    public TooLongNicknameException(final String nickname) {
        super(String.format("너무 긴 닉네임입니다. %s = ", nickname));
    }
}
