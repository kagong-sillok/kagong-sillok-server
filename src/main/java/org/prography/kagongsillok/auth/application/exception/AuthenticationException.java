package org.prography.kagongsillok.auth.application.exception;

import org.prography.kagongsillok.common.exception.BusinessException;

public class AuthenticationException extends BusinessException {

    public AuthenticationException(final String message) {
        super(message);
    }
}
