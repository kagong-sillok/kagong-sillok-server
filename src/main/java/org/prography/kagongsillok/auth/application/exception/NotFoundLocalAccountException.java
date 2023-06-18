package org.prography.kagongsillok.auth.application.exception;

import org.prography.kagongsillok.common.exception.NotFoundException;

public final class NotFoundLocalAccountException extends NotFoundException {

    public NotFoundLocalAccountException(final String loginId) {
        super(String.format("존재하지 않는 로컬 계정입니다. loginId = %s", loginId));
    }
}
