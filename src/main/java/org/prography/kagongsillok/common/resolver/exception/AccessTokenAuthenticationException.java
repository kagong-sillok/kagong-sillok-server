package org.prography.kagongsillok.common.resolver.exception;

import org.prography.kagongsillok.auth.application.exception.AuthenticationException;

public class AccessTokenAuthenticationException extends AuthenticationException {

    public AccessTokenAuthenticationException() {
        super(String.format("Header에 AccessToken이 존재하지 않습니다."));
    }
}
