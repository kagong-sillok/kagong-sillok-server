package org.prography.kagongsillok.common.exception;

public abstract class BusinessException extends RuntimeException {

    public BusinessException(final String message) {
        super(message);
    }
}
