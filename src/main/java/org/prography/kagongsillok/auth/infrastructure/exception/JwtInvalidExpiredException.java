package org.prography.kagongsillok.auth.infrastructure.exception;

import org.prography.kagongsillok.common.exception.InvalidParamException;

public final class JwtInvalidExpiredException extends InvalidParamException {

    public JwtInvalidExpiredException() {
        super("토큰의 기간이 만료되었습니다.");
    }
}
