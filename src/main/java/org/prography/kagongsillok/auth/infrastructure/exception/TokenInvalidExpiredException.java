package org.prography.kagongsillok.auth.infrastructure.exception;

import org.prography.kagongsillok.common.exception.InvalidParamException;

public final class TokenInvalidExpiredException extends InvalidParamException {

    public TokenInvalidExpiredException() {
        super("토큰의 기간이 만료되었습니다.");
    }
}
