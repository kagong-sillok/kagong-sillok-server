package org.prography.kagongsillok.auth.application.exception;

public class NotLoginMemberException extends AuthenticationException {

    public NotLoginMemberException(final String message) {
        super(message);
    }
}
