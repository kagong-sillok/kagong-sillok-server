package org.prography.kagongsillok.auth.infrastructure.exception;

import org.prography.kagongsillok.common.exception.CommonSecurityException;

public final class JwtInvalidSecretKeyException extends CommonSecurityException {

    public JwtInvalidSecretKeyException(final String token) {
        super(String.format("변조된 JWT입니다. 해킹 시도일 수 있습니다. token = %s", token));
    }
}
