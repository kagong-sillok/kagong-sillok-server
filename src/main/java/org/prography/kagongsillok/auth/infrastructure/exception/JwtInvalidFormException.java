package org.prography.kagongsillok.auth.infrastructure.exception;

import org.prography.kagongsillok.common.exception.CommonSecurityException;

public final class JwtInvalidFormException extends CommonSecurityException {

    public JwtInvalidFormException(final String token) {
        super(String.format("JWT 형식이 일치하지 않습니다. token = %s", token));
    }
}
